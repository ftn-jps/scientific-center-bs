package ftnjps.scientificcenter.article;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.BooleanUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder.Item;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
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
		request.mapping("_doc", "location", "type=geo_point");
		try {
			elasticClient.indices().create(request, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean index(Article article) {
		String authors = article.getAuthor().getName() + " "
				+ article.getAuthor().getLastName()
				+ ", ";
		for(ApplicationUser coauthor : article.getCoauthors()) {
			authors += coauthor.getName() + " "
					+ coauthor.getLastName()
					+ ", ";
		}
		String pdfContent = fileUtils.readFromFile(article.getPdfName());
		IndexRequest indexRequest = new IndexRequest("articles", "_doc", article.getId().toString())
				.source("journalName", article.getJournal().getName(),
						"title", article.getTitle(),
						"keywords", article.getKeywords(),
						"articleAbstract", article.getArticleAbstract(),
						"pdfContent", pdfContent,
						"fieldOfStudy", article.getFieldOfStudy().getName(),
						"authors", authors,
						"location", article.getAuthor().getLocation());
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
		return search(
				QueryBuilders.simpleQueryStringQuery(query).defaultOperator(Operator.AND),
				payer);
	}

	/**
	 * Search Article fields using Bool Query
	 * @param query
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleDto> searchAdvanced(Map<String, Object> query, ApplicationUser payer) {
		BoolQueryBuilder compoundQuery = QueryBuilders.boolQuery();
		for(String key : query.keySet()) {
			Map<String, Object> field = (Map<String, Object>) query.get(key);

			QueryBuilder leafQuery = null;
			key = key.equals("attachment") ? key + ".content" : key;
			if( BooleanUtils.isTrue((Boolean) field.get("isPhrase"))) {
				leafQuery = QueryBuilders.matchPhraseQuery(key, field.get("query"));
			}
			else {
				leafQuery = QueryBuilders.matchQuery(key, field.get("query"));
			}

			if( BooleanUtils.isTrue((Boolean) field.get("isOptional"))) {
				compoundQuery.should(leafQuery);
			}
			else {
				compoundQuery.must(leafQuery);
			}
		}
		return search(compoundQuery, payer);
	}

	/**
	 * Search using More Like This Query
	 * @param query
	 */
	@Override
	public List<ArticleDto> searchMoreLikeThis(Long articleId, ApplicationUser payer) {
		Item[] items = new Item[] {
				new Item("articles", "_doc", articleId.toString()).fields("attachment.content")
			};
		return search(
				QueryBuilders.moreLikeThisQuery(items).minDocFreq(2),
				payer);
	}

	/**
	 * Search using Geo Distance Query
	 * @param query
	 */
	@Override
	public List<ArticleDto> searchGeodistance(String location, ApplicationUser payer) {
		BoolQueryBuilder compoundQuery = QueryBuilders.boolQuery();
		compoundQuery.must(QueryBuilders.matchAllQuery());
		compoundQuery.mustNot(
				QueryBuilders.geoDistanceQuery("location")
					.point(new GeoPoint(location))
					.distance(100, DistanceUnit.KILOMETERS));

		return search(
				compoundQuery,
				payer);
	}

	private List<ArticleDto> search(QueryBuilder query, ApplicationUser payer) {
		SearchRequest searchRequest = new SearchRequest("articles");

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(query);

		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("journalName");
		highlightBuilder.field("title");
		highlightBuilder.field("keywords");
		highlightBuilder.field("articleAbstract");
		highlightBuilder.field("attachment.content");
		highlightBuilder.field("fieldOfStudy");
		highlightBuilder.field("authors");
		highlightBuilder.requireFieldMatch(true);
		highlightBuilder.preTags("<span class=\"highlight\">");
		highlightBuilder.postTags("</span>");
		searchSourceBuilder.highlighter(highlightBuilder);

		searchRequest.source(searchSourceBuilder);

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
