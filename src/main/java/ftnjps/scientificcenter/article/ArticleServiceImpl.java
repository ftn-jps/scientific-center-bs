package ftnjps.scientificcenter.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ftnjps.scientificcenter.users.ApplicationUser;

@Transactional(readOnly = true)
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private ArticleConverter articleConverter;

	@Override
	public Article findOne(Long id) {
		return articleRepository.findById(id).orElse(null);
	}

	@Override
	public List<ArticleDto> findAll(ApplicationUser payer) {
		List<Article> articles = articleRepository.findAll();
		return articleConverter.toDto(articles, payer);
	}

	@Override
	@Transactional(readOnly = false)
	public Article add(Article article) {
		return articleRepository.save(article);
	}

}
