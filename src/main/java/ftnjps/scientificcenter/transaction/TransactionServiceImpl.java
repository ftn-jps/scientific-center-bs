package ftnjps.scientificcenter.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ftnjps.scientificcenter.article.Article;
import ftnjps.scientificcenter.users.ApplicationUser;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Value("${server.url}")
	private String serverUrl;

	@Override
	public Transaction findOne(Long id) {
		return transactionRepository.findById(id).orElse(null);
	}

	@Override
	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}

	@Override
	public Transaction findBySuccessToken(String token) {
		return transactionRepository.findBySuccessToken(token);
	}

	@Override
	public Transaction findByErrorToken(String token) {
		return transactionRepository.findByErrorToken(token);
	}

	@Override
	public List<Transaction> findPaidByPayer(ApplicationUser payer) {
		return transactionRepository.findByPayerAndIsFinalized(payer, true);
	}

	@Override
	public Transaction addArticleTransaction(Article article, ApplicationUser payer) {
		Transaction transaction = new Transaction(
				article.getJournal().getPrice(),
				article.getJournal().getIssn(),
				null,
				article.getId().intValue(),
				payer);
		transaction.setSuccessUrl(serverUrl + "/api/transactions/success/" + transaction.getSuccessToken());
		transaction.setErrorUrl(serverUrl + "/api/transactions/error/" + transaction.getErrorToken());
		transaction.setFailUrl(transaction.getErrorUrl());

		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction finalizeArticleTransaction(Transaction input) {
		// Finalized transactions have token == null
		input.setFinalized(true);
		return transactionRepository.save(input);
	}

	@Override
	public void removeArticleTransaction(Transaction input) {
		transactionRepository.delete(input);;
	}

}
