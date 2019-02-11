package ftnjps.scientificcenter.article;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ftnjps.scientificcenter.transaction.TransactionService;
import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.utils.FileUtils;

@Transactional(readOnly = true)
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private ArticleConverter articleConverter;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private FileUtils fileUtils;
	@Autowired
	RestHighLevelClient elasticClient;

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
	public String getPdf(Long id, ApplicationUser forUser) {
		Article article = articleRepository.findById(id).orElse(null);
		if(article == null)
			return null;
		if( ! transactionService.checkPermission(article, forUser))
			return null;
		return fileUtils.readFromFile(article.getPdfName());
	}

	@Override
	@Transactional(readOnly = false)
	public Article add(Article article) {
		if(article.getPdfContent() != null) {
			String fileName = UUID.randomUUID().toString();
			fileUtils.writeToFile(article.getPdfContent(), fileName);
			article.setPdfName(fileName);
		}
		article = articleRepository.save(article);
		index(article);
		return article;
	}

	@Override
	public void createIndex() {
		CreateIndexRequest request = new CreateIndexRequest("articles");
		request.settings(Settings.builder()
				.put("analysis.analyzer.default.type", "serbian_analyzer"));
		try {
			elasticClient.indices().create(request, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean index(Article article) {
		String pdfContent = fileUtils.readFromFile(article.getPdfName());
		IndexRequest indexRequest = new IndexRequest("articles", "_doc", article.getId().toString())
				.source("journalName", article.getJournal().getName(),
						"title", article.getTitle(),
						"keywords", article.getKeywords(),
						"articleAbstract", article.getArticleAbstract(),
						"pdfContent", pdfContent,
						"fieldOfStudy", article.getFieldOfStudy().getName());
		indexRequest.setPipeline("attachment");
		try {
			elasticClient.index(indexRequest, RequestOptions.DEFAULT);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Search all Article fields using Simple Query String Query
	 * @param query
	 */
	@Override
	public List<ArticleDto> searchAll(String query, ApplicationUser payer) {
		SearchRequest searchRequest = new SearchRequest("articles");

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.simpleQueryStringQuery(query));
		searchRequest.source(searchSourceBuilder);

		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field(new HighlightBuilder.Field("journalName"));
		highlightBuilder.field(new HighlightBuilder.Field("title"));
		highlightBuilder.field(new HighlightBuilder.Field("keywords"));
		highlightBuilder.field(new HighlightBuilder.Field("articleAbstract"));
		highlightBuilder.field(new HighlightBuilder.Field("attachment.content"));
		highlightBuilder.field(new HighlightBuilder.Field("fieldOfStudy"));
		highlightBuilder.preTags("<span class=\"highlight\">");
		highlightBuilder.postTags("</span>");
		searchSourceBuilder.highlighter(highlightBuilder);

		try {
			SearchResponse searchResponse = elasticClient.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits hits = searchResponse.getHits();
			return articleConverter.toDto(hits, payer);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
