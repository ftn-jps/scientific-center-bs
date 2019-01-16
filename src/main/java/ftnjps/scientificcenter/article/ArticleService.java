package ftnjps.scientificcenter.article;

import java.util.List;

public interface ArticleService {

	Article findOne(Long id);

	List<Article> findAll();

	Article add(Article article);

}
