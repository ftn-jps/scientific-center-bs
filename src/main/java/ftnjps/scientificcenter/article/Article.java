package ftnjps.scientificcenter.article;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ftnjps.scientificcenter.fieldofstudy.FieldOfStudy;
import ftnjps.scientificcenter.journal.Journal;
import ftnjps.scientificcenter.users.ApplicationUser;

@Entity
public class Article {
	@Id
	@GeneratedValue
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@Version
	private Long version;

	private String title;

	@ManyToOne
	@NotNull
	private ApplicationUser author;

	@ManyToOne
	@NotNull
	private Journal journal;

	@ManyToMany
	@NotNull
	private Set<ApplicationUser> coauthors = new HashSet<>();

	private String keywords;

	private String articleAbstract;

	@ManyToOne
	@NotNull
	private FieldOfStudy fieldOfStudy;

	@NotNull
	@JsonIgnore
	private String pdfName;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Transient
	private String pdfContent;

	public Article() {}

	public Article(String title,
			ApplicationUser author,
			Journal journal,
			String keywords,
			String articleAbstract,
			FieldOfStudy fieldOfStudy,
			String pdfName) {
		super();
		this.title = title;
		this.author = author;
		this.journal = journal;
		this.keywords = keywords;
		this.articleAbstract = articleAbstract;
		this.fieldOfStudy = fieldOfStudy;
		this.pdfName = pdfName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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

	public void addCoauthor(ApplicationUser coauthor) {
		this.coauthors.add(coauthor);
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

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public String getPdfContent() {
		return pdfContent;
	}

	public void setPdfContent(String pdfContent) {
		this.pdfContent = pdfContent;
	}

}
