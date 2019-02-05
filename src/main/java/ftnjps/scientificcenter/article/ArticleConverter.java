package ftnjps.scientificcenter.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.transaction.Transaction;
import ftnjps.scientificcenter.transaction.TransactionService;
import ftnjps.scientificcenter.users.ApplicationUser;

@Component
public class ArticleConverter {

	@Autowired
	private TransactionService transactionService;

	List<ArticleDto> toDto(List<Article> articles, ApplicationUser payer) {
		List<Transaction> transactions;
		if(payer == null)
			transactions = new ArrayList<>();
		else
			transactions = transactionService.findPaidByPayer(payer);
		HashMap<Integer, Transaction> transactionsMap = new HashMap<>();
		for(Transaction transaction : transactions) {
			transactionsMap.put(transaction.getMerchantOrderId(), transaction);
		}

		List<ArticleDto> result = new ArrayList<>();
		for(Article article : articles) {
			if(transactionsMap.containsKey(article.getId().intValue())
					|| article.getJournal().isOpenAccess()) {
				result.add(toDto(article, true));
			}
			else {
				result.add(toDto(article, false));
			}
		}
		return result;
	}

	ArticleDto toDto(Article article, boolean hasAccess) {
		return new ArticleDto(article.getId(),
				article.getTitle(),
				article.getAuthor(),
				article.getJournal(),
				article.getKeywords(),
				article.getArticleAbstract(),
				article.getFieldOfStudy(),
				hasAccess);
	}

}
