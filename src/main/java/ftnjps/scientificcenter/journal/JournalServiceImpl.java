package ftnjps.scientificcenter.journal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class JournalServiceImpl implements JournalService {

	@Autowired
	private JournalRepository journalRepository;

	@Override
	public Journal findOne(Long id) {
		return journalRepository.findById(id).orElse(null);
	}

	@Override
	public Journal findByIssn(String issn) {
		return journalRepository.findByIssn(issn);
	}

	@Override
	public Journal findByName(String name) {
		return journalRepository.findByName(name);
	}

	@Override
	public List<Journal> findAll() {
		return journalRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public Journal add(Journal journal) {
		Journal existing = findByIssn(journal.getIssn());
		if(existing != null) // Journal already exists
			return null;

		return journalRepository.save(journal);
	}

}
