package ftnjps.scientificcenter.fieldofstudy;

import java.util.List;

public interface FieldOfStudyService {

	FieldOfStudy findOne(Long id);

	FieldOfStudy findByName(String name);

	List<FieldOfStudy> findAll();

	FieldOfStudy add(FieldOfStudy field);

}
