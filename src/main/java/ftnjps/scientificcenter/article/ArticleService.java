package ftnjps.scientificcenter.article;

import java.util.List;
import java.util.Map;

import ftnjps.scientificcenter.users.ApplicationUser;

public interface ArticleService {

	Article findOne(Long id);

	List<ArticleDto> findAll(ApplicationUser payer);

	String getPdf(Long id, ApplicationUser forUser);

	Article add(Article article);

	void createIndex();
	boolean index(Article article);
	List<ArticleDto> searchAll(String query, ApplicationUser payer);
	List<ArticleDto> searchAdvanced(Map<String, Object> query, ApplicationUser payer);
	List<ArticleDto> searchMoreLikeThis(Long articleId, ApplicationUser payer);
	List<ArticleDto> searchGeodistance(String location, ApplicationUser payer);

}
