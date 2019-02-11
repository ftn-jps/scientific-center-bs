package ftnjps.scientificcenter.article;

import java.util.List;
import java.util.UUID;

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

}
