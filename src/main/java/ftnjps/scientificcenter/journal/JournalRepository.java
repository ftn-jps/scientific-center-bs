package ftnjps.scientificcenter.journal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

	Journal findByIssn(String issn);
	Journal findByName(String name);

}
