package ftnjps.scientificcenter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.article.Article;
import ftnjps.scientificcenter.article.ArticleService;
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
	@Autowired
	private ArticleService articleService;

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

		ApplicationUser reviewer2 = new ApplicationUser(
				"reviewer2@local",
				"1234qwerQWER",
				"Reviewer",
				"Two",
				"Belgrade",
				"Serbia");
		reviewer2.setTitle("Ph.D.");
		reviewer2.addFieldOfStudy(fieldAstronomy);
		reviewer2.setUserType(ApplicationUserType.REVIEWER);
		reviewer2 = applicationUserService.add(reviewer2);

		ApplicationUser reviewer3 = new ApplicationUser(
				"reviewer3@local",
				"1234qwerQWER",
				"Reviewer",
				"Three",
				"Nis",
				"Serbia");
		reviewer3.setTitle("Ph.D.");
		reviewer3.addFieldOfStudy(fieldPhysics);
		reviewer3.setUserType(ApplicationUserType.REVIEWER);
		reviewer3 = applicationUserService.add(reviewer3);

		ApplicationUser author1 = new ApplicationUser(
				"author1@local",
				"1234qwerQWER",
				"Author",
				"One",
				"Novi Sad",
				"Serbia");
		author1.setTitle("Ph.D.");
		author1.addFieldOfStudy(fieldComputer);
		author1.setUserType(ApplicationUserType.AUTHOR);
		author1 = applicationUserService.add(author1);

		ApplicationUser author2 = new ApplicationUser(
				"author2@local",
				"1234qwerQWER",
				"Ричард",
				"Сталман",
				"Cambridge",
				"USA");
		author2.setTitle("Ph.D.");
		author2.addFieldOfStudy(fieldComputer);
		author2.setUserType(ApplicationUserType.AUTHOR);
		author2 = applicationUserService.add(author2);

		ApplicationUser author3 = new ApplicationUser(
				"author3@local",
				"1234qwerQWER",
				"Bojan",
				"Stipic",
				"Novi Sad",
				"Serbia");
		author3.setTitle("B.Sc.");
		author3.addFieldOfStudy(fieldComputer);
		author3.setUserType(ApplicationUserType.AUTHOR);
		author3 = applicationUserService.add(author3);

		Journal journal1 = new Journal("1111-1111", "Journal One", false, editor1, 5);
		journal1.addFieldOfStudy(fieldComputer);
		journal1.addFieldOfStudy(fieldMathematics);
		journal1.addEditor(editor2);
		journal1.addReviewer(reviewer1);
		journal1.addReviewer(reviewer2);
		journal1 = journalService.add(journal1);

		Journal journal2 = new Journal("2222-2222", "Journal Two", true, editor3, 5);
		journal2.addFieldOfStudy(fieldComputer);
		journal2.addFieldOfStudy(fieldPhysics);
		journal2.addReviewer(reviewer2);
		journal2.addReviewer(reviewer3);
		journal2 = journalService.add(journal2);

		Journal journal3 = new Journal("3333-3333", "Journal Three", true, editor2, 10);
		journal3.addFieldOfStudy(fieldMedicine);
		journal3.addReviewer(reviewer2);
		journal3.addReviewer(reviewer3);
		journal3 = journalService.add(journal3);

		Journal journal4 = new Journal("4444-4444", "Journal Two", false, editor3, 5);
		journal4.addFieldOfStudy(fieldGeography);
		journal4.addFieldOfStudy(fieldChemistry);
		journal4.addFieldOfStudy(fieldBiology);
		journal4.addReviewer(reviewer1);
		journal4.addReviewer(reviewer2);
		journal4 = journalService.add(journal4);

		ApplicationUser coauthor1 = new ApplicationUser(
				"coauthor1@local",
				null,
				"Coauthor",
				"One",
				"Barcelona",
				"Spain");
		coauthor1.setUserType(ApplicationUserType.COAUTHOR);
		coauthor1 = applicationUserService.add(coauthor1);

		Article article1 = new Article("Naučna centrala: Specifikacija projekta",
				author1,
				journal1,
				"naučna centrala, programiranje, projekat, specifikacija",
				"Specifikacija projekta na master studijama na Fakultetu tehničkih nauka",
				fieldComputer,
				"naučna-centrala.pdf");
		article1.addCoauthor(coauthor1);
		article1 = articleService.add(article1);

		Article article2 = new Article("Шта је слободни софтвер?",
				author2,
				journal2,
				"ГНУ, слобода, софтвер, дефиниција",
				"Дефиниција слободног софтвера; четири основне слободе",
				fieldComputer,
				"free-sw.pdf");
		article2 = articleService.add(article2);

		Article article3 = new Article("Зашто софтвер треба да буде слободан",
				author2,
				journal2,
				"ГНУ, слобода, софтвер, зашто",
				"Како власници правдају своју моћ; Аргумент против власништва",
				fieldComputer,
				"shouldbefree.pdf");
		article3 = articleService.add(article3);

		Article article4 = new Article("Зашто је „слободни софтвер“ бољи од „отвореног изворног кода“",
				author2,
				journal2,
				"ГНУ, слобода, отворен изворни код",
				"Однос између Покрета за слободни софтвер и Покрета за отворени изворни код.",
				fieldComputer,
				"free-software-for-freedom.pdf");
		article4 = articleService.add(article4);

		Article article5 = new Article("Збуњујуће речи и синтагме које би требало избегавати",
				author2,
				journal2,
				"ГНУ, софтвер, збуњујуће речи",
				"Постоје речи и синтагме које препоручујемо да избегавате, или их избегавате у одређеним контекстима и приликама.",
				fieldComputer,
				"words-to-avoid.pdf");
		article5 = articleService.add(article5);

		Article article6 = new Article("Врсте слободног и неслободног софтвера",
				author2,
				journal2,
				"ГНУ, софтвер, копилефт, власнички, јавно власништво, Фривер",
				"Врсте слободног и неслободног софтвера.",
				fieldComputer,
				"categories.pdf");
		article6 = articleService.add(article6);

		Article article7 = new Article("Penetraciono testiranje web aplikacija i servera",
				author3,
				journal1,
				"Penetraciono testiranje, web, server, bezbednost",
				"Penetraciono testiranje web aplikacija i servera upotrebom Nmap i Nikto alata.",
				fieldComputer,
				"penetraciono-testiranje.pdf");
		article7 = articleService.add(article7);
	}
}
