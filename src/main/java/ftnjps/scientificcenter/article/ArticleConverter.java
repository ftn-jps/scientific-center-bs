package ftnjps.scientificcenter.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
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

	public ArticleDto toDto(Article article, boolean hasAccess, String searchPreview) {
		return new ArticleDto(article.getId(),
				article.getTitle(),
				article.getAuthor(),
				article.getJournal(),
				article.getKeywords(),
				article.getArticleAbstract(),
				article.getFieldOfStudy(),
				hasAccess,
				searchPreview);
	}

	public List<ArticleDto> toDto(SearchHits hits, ApplicationUser payer) {
		List<ArticleDto> result = new ArrayList<>();
		for(SearchHit hit : hits) {
			Article article = articleService.findOne(Long.parseLong(hit.getId()));

			// Get highlight preview
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			String searchPreview = "";
			for(HighlightField highlight : highlightFields.values()) {
				Text[] fragments = highlight.fragments();
				for(Text fragment : fragments) {
					searchPreview += fragment.string() + " … ";
				}
			}

			if(transactionService.checkPermission(article, payer)) {
				result.add(toDto(article, true, searchPreview));
			}
			else {
				result.add(toDto(article, false, searchPreview));
			}
		}
		return result;
	}

}
