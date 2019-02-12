package ftnjps.scientificcenter.article;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.users.ApplicationUserService;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ApplicationUserService userService;

	@GetMapping
	public ResponseEntity<List<ArticleDto>> getAll(Principal principal) {
		ApplicationUser payer;
		if(principal == null)
			payer = null;
		else
			payer = userService.findByEmail(principal.getName());

		List<ArticleDto> articles = articleService.findAll(payer);
		if(articles == null || articles.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(articles, HttpStatus.OK);
	}

	@GetMapping("/{articleId}")
	public ResponseEntity<?> getArticle(Principal principal,
			@PathVariable Long articleId) {
		ApplicationUser user;
		if(principal == null)
			user = null;
		else
			user = userService.findByEmail(principal.getName());

		String pdfContent = articleService.getPdf(articleId, user);
		if(pdfContent == null || pdfContent.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		HashMap<String, String> ret = new HashMap<>();
		ret.put("pdfContent", pdfContent);
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@GetMapping("/search/{query}")
	public ResponseEntity<List<ArticleDto>> searchAll(Principal principal,
			@PathVariable String query) {
		ApplicationUser payer;
		if(principal == null)
			payer = null;
		else
			payer = userService.findByEmail(principal.getName());

		List<ArticleDto> articles = articleService.searchAll(query, payer);
		if(articles == null || articles.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(articles, HttpStatus.OK);
	}

	@PutMapping("/search/")
	public ResponseEntity<List<ArticleDto>> searchAdvanced(Principal principal,
			@RequestBody Map<String, Object> query) {
		ApplicationUser payer;
		if(principal == null)
			payer = null;
		else
			payer = userService.findByEmail(principal.getName());

		List<ArticleDto> articles = articleService.searchAdvanced(query, payer);
		if(articles == null || articles.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(articles, HttpStatus.OK);
	}

	@GetMapping("/search/more-like-this/{articleId}")
	public ResponseEntity<List<ArticleDto>> searchAll(Principal principal,
			@PathVariable Long articleId) {
		ApplicationUser payer;
		if(principal == null)
			payer = null;
		else
			payer = userService.findByEmail(principal.getName());

		List<ArticleDto> articles = articleService.searchMoreLikeThis(articleId, payer);
		if(articles == null || articles.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(articles, HttpStatus.OK);
	}

	@GetMapping("/search/geodistance")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<ArticleDto>> searchGeodistance(Principal principal) {
		ApplicationUser payer = userService.findByEmail(principal.getName());

		List<ArticleDto> articles = articleService.searchGeodistance(payer.getLocation(), payer);
		if(articles == null || articles.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(articles, HttpStatus.OK);
	}

}
