package ftnjps.scientificcenter.fieldofstudy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class FieldOfStudyServiceImpl implements FieldOfStudyService {

	@Autowired
	private FieldOfStudyRepository fieldOfStudyRepository;

	@Override
	public FieldOfStudy findOne(Long id) {
		return fieldOfStudyRepository.findById(id).orElse(null);
	}

	@Override
	public FieldOfStudy findByName(String name) {
		return fieldOfStudyRepository.findByName(name);
	}

	@Override
	public List<FieldOfStudy> findAll() {
		return fieldOfStudyRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public FieldOfStudy add(FieldOfStudy field) {
		FieldOfStudy existing = findByName(field.getName());
		if(existing != null) // Field already exists
			return null;

		return fieldOfStudyRepository.save(field);
	}

}
