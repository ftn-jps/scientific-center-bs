package ftnjps.scientificcenter.fieldofstudy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {

	FieldOfStudy findByName(String name);

}
