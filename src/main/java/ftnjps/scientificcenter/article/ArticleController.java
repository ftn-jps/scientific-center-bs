package ftnjps.scientificcenter.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping
	public ResponseEntity<List<Article>> getAll() {
		List<Article> articles = articleService.findAll();
		if(articles == null || articles.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(articles, HttpStatus.OK);
	}

}
