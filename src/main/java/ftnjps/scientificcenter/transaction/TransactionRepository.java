package ftnjps.scientificcenter.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftnjps.scientificcenter.users.ApplicationUser;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	Transaction findBySuccessToken(String successToken);
	Transaction findByErrorToken(String errorToken);
	List<Transaction> findByPayerAndIsFinalized(ApplicationUser payer, boolean isFinalized);
	Transaction findByPayerAndMerchantOrderIdAndIsFinalized(ApplicationUser payer, int merchantOrderId, boolean isFinalized);

}
