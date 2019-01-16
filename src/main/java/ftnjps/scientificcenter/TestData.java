package ftnjps.scientificcenter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.fieldofstudy.FieldOfStudy;
import ftnjps.scientificcenter.fieldofstudy.FieldOfStudyService;
import ftnjps.scientificcenter.journal.Journal;
import ftnjps.scientificcenter.journal.JournalService;
import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.users.ApplicationUserService;
import ftnjps.scientificcenter.users.ApplicationUserType;

@Component
public class TestData {

	@Autowired
	private FieldOfStudyService fieldOfStudyService;
	@Autowired
	private ApplicationUserService applicationUserService;
	@Autowired
	private JournalService journalService;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		FieldOfStudy fieldAstronomy = fieldOfStudyService.add(
				new FieldOfStudy("Astronomy"));
		FieldOfStudy fieldBiology = fieldOfStudyService.add(
				new FieldOfStudy("Biology"));
		FieldOfStudy fieldChemistry = fieldOfStudyService.add(
				new FieldOfStudy("Chemistry"));
		FieldOfStudy fieldComputer = fieldOfStudyService.add(
				new FieldOfStudy("Computer Science"));
		FieldOfStudy fieldGeography= fieldOfStudyService.add(
				new FieldOfStudy("Geography"));
		FieldOfStudy fieldMathematics = fieldOfStudyService.add(
				new FieldOfStudy("Mathematics"));
		FieldOfStudy fieldMedicine = fieldOfStudyService.add(
				new FieldOfStudy("Medicine"));
		FieldOfStudy fieldPhysics = fieldOfStudyService.add(
				new FieldOfStudy("Physics"));

		ApplicationUser editor1 = new ApplicationUser(
				"editor1@local",
				"1234qwerQWER",
				"Editor",
				"One",
				"Novi Sad",
				"Serbia");
		editor1.setTitle("Ph.D.");
		editor1.addFieldOfStudy(fieldComputer);
		editor1.setUserType(ApplicationUserType.EDITOR);
		editor1 = applicationUserService.add(editor1);

		ApplicationUser editor2 = new ApplicationUser(
				"editor2@local",
				"1234qwerQWER",
				"Editor",
				"Two",
				"Belgrade",
				"Serbia");
		editor2.setTitle("Ph.D.");
		editor2.addFieldOfStudy(fieldMathematics);
		editor2.setUserType(ApplicationUserType.EDITOR);
		editor2 = applicationUserService.add(editor2);

		ApplicationUser editor3 = new ApplicationUser(
				"editor3@local",
				"1234qwerQWER",
				"Editor",
				"Three",
				"Nis",
				"Serbia");
		editor3.setTitle("Ph.D.");
		editor3.addFieldOfStudy(fieldPhysics);
		editor3.setUserType(ApplicationUserType.EDITOR);
		editor3 = applicationUserService.add(editor3);

		ApplicationUser reviewer1 = new ApplicationUser(
				"reviewer1@local",
				"1234qwerQWER",
				"Reviewer",
				"One",
				"Novi Sad",
				"Serbia");
		reviewer1.setTitle("Ph.D.");
		reviewer1.addFieldOfStudy(fieldComputer);
		reviewer1.setUserType(ApplicationUserType.REVIEWER);
		reviewer1 = applicationUserService.add(reviewer1);

		Journal journal1 = new Journal("1111-1111", "Journal One", false, editor1);
		journal1.addFieldOfStudy(fieldComputer);
		journal1.addFieldOfStudy(fieldMathematics);
		journal1.addEditor(editor2);
		journal1.addReviewer(reviewer1);
		journal1 = journalService.add(journal1);

	}
}
