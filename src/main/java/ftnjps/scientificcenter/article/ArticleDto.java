package ftnjps.scientificcenter.article;

import java.util.HashSet;
import java.util.Set;

import ftnjps.scientificcenter.fieldofstudy.FieldOfStudy;
import ftnjps.scientificcenter.journal.Journal;
import ftnjps.scientificcenter.users.ApplicationUser;

public class ArticleDto {

	private Long id;
	private String title;
	private ApplicationUser author;
	private Journal journal;
	private Set<ApplicationUser> coauthors = new HashSet<>();
	private String keywords;
	private String articleAbstract;
	private FieldOfStudy fieldOfStudy;

	private boolean hasAccess;

	public ArticleDto() {}

	public ArticleDto(Long id,
			String title,
			ApplicationUser author,
			Journal journal,
			String keywords,
			String articleAbstract,
			FieldOfStudy fieldOfStudy,
			boolean hasAccess) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.journal = journal;
		this.keywords = keywords;
		this.articleAbstract = articleAbstract;
		this.fieldOfStudy = fieldOfStudy;
		this.hasAccess = hasAccess;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ApplicationUser getAuthor() {
		return author;
	}

	public void setAuthor(ApplicationUser author) {
		this.author = author;
	}

	public void addCoauthor(ApplicationUser coauthor) {
		this.coauthors.add(coauthor);
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Set<ApplicationUser> getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(Set<ApplicationUser> coauthors) {
		this.coauthors = coauthors;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getArticleAbstract() {
		return articleAbstract;
	}

	public void setArticleAbstract(String articleAbstract) {
		this.articleAbstract = articleAbstract;
	}

	public FieldOfStudy getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	public boolean isHasAccess() {
		return hasAccess;
	}

	public void setHasAccess(boolean hasAccess) {
		this.hasAccess = hasAccess;
	}
}
