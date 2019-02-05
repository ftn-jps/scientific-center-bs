package ftnjps.scientificcenter.article;

import java.util.List;

import ftnjps.scientificcenter.users.ApplicationUser;

public interface ArticleService {

	Article findOne(Long id);

	List<ArticleDto> findAll(ApplicationUser payer);

	Article add(Article article);

}
