package ftnjps.scientificcenter.article;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
