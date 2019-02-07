package ftnjps.scientificcenter.transaction;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ftnjps.scientificcenter.article.Article;
import ftnjps.scientificcenter.article.ArticleService;
import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.users.ApplicationUserService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private ApplicationUserService userService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	RestTemplate restClientSelfSigned;
	@Value("${gateway.url}")
	private String gatewayUrl;

	@PostMapping("/{articleId}")
	public ResponseEntity<?> startTransaction(Principal principal,
			@PathVariable Long articleId) {
		if(principal == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		ApplicationUser payer = userService.findByEmail(principal.getName());

		Article article = articleService.findOne(articleId);
		if(article == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		Transaction transaction = transactionService.addArticleTransaction(article, payer);

		URI response = restClientSelfSigned.postForLocation(
				gatewayUrl + "/api/transactions",
				transaction);
		String paymentUrl = response.toString();
		HashMap<String,String> result = new HashMap<>();
		result.put("paymentUrl", paymentUrl);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/success/{token}")
	public ResponseEntity<?> finalizeTransaction(@PathVariable String token) {
		Transaction transaction = transactionService.findBySuccessToken(token);
		if(transaction == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		transactionService.finalizeArticleTransaction(transaction);
		return new ResponseEntity<>(
				"Payment successful. Return to the <a href=\"/\">homepage</a>", HttpStatus.OK);
	}

	@GetMapping("/error/{token}")
	public ResponseEntity<?> removeTransaction(@PathVariable String token) {
		Transaction transaction = transactionService.findByErrorToken(token);
		if(transaction == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		transactionService.removeArticleTransaction(transaction);
		return new ResponseEntity<>(
				"Payment unsuccessful. Return to the <a href=\"/\">homepage</a>", HttpStatus.OK);
	}

}
