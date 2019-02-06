package ftnjps.scientificcenter.transaction;

import java.util.List;

import ftnjps.scientificcenter.article.Article;
import ftnjps.scientificcenter.users.ApplicationUser;

public interface TransactionService {

	Transaction findOne(Long id);
	List<Transaction> findAll();
	Transaction findBySuccessToken(String token);
	Transaction findByErrorToken(String token);
	List<Transaction> findPaidByPayer(ApplicationUser payer);
	boolean checkPermission(Article article, ApplicationUser forUser);
	Transaction addArticleTransaction(Article input, ApplicationUser payer);
	Transaction finalizeArticleTransaction(Transaction input);
	void removeArticleTransaction(Transaction input);
}
