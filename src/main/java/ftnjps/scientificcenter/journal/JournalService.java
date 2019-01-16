package ftnjps.scientificcenter.journal;

import java.util.List;

public interface JournalService {

	Journal findOne(Long id);

	Journal findByIssn(String issn);

	Journal findByName(String name);

	List<Journal> findAll();

	Journal add(Journal journal);

}
