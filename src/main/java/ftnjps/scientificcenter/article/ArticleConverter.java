package ftnjps.scientificcenter.article;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.transaction.TransactionService;
import ftnjps.scientificcenter.users.ApplicationUser;

@Component
public class ArticleConverter {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private ArticleService articleService;

	public List<ArticleDto> toDto(List<Article> articles, ApplicationUser payer) {
		List<ArticleDto> result = new ArrayList<>();
		for(Article article : articles) {
			if(transactionService.checkPermission(article, payer)) {
				result.add(toDto(article, true, null));
			}
			else {
				result.add(toDto(article, false, null));
			}
		}
		return result;
	}

	public ArticleDto toDto(Article article, boolean hasAccess, String pdfPreview) {
		return new ArticleDto(article.getId(),
				article.getTitle(),
				article.getAuthor(),
				article.getJournal(),
				article.getKeywords(),
				article.getArticleAbstract(),
				article.getFieldOfStudy(),
				hasAccess,
				pdfPreview);
	}

}
