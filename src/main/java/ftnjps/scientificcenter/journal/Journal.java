package ftnjps.scientificcenter.journal;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ftnjps.scientificcenter.fieldofstudy.FieldOfStudy;
import ftnjps.scientificcenter.users.ApplicationUser;

@Entity
public class Journal {
	@Id
	@GeneratedValue
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@Version
	private Long version;

	@NotBlank
	private String issn;

	private String name;

	@ManyToMany
	private Set<FieldOfStudy> fieldsOfStudy = new HashSet<>();

	boolean isOpenAccess;

	@NotNull
	@ManyToOne
	private ApplicationUser mainEditor;
	@ManyToMany
	private Set<ApplicationUser> editors = new HashSet<>();
	@ManyToMany
	private Set<ApplicationUser> reviewers = new HashSet<>();

	private double price;

	public Journal() {}

	public Journal(String issn,
			String name,
			boolean isOpenAccess,
			ApplicationUser mainEditor,
			double price) {
		this.issn = issn;
		this.name = name;
		this.isOpenAccess = isOpenAccess;
		this.mainEditor = mainEditor;
		this.price = price;
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

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<FieldOfStudy> getFieldsOfStudy() {
		return fieldsOfStudy;
	}

	public void setFieldsOfStudy(Set<FieldOfStudy> fieldsOfStudy) {
		this.fieldsOfStudy = fieldsOfStudy;
	}

	public void addFieldOfStudy(FieldOfStudy field) {
		this.fieldsOfStudy.add(field);
	}

	public boolean isOpenAccess() {
		return isOpenAccess;
	}

	public void setOpenAccess(boolean isOpenAccess) {
		this.isOpenAccess = isOpenAccess;
	}

	public ApplicationUser getMainEditor() {
		return mainEditor;
	}

	public void setMainEditor(ApplicationUser mainEditor) {
		this.mainEditor = mainEditor;
	}

	public Set<ApplicationUser> getEditors() {
		return editors;
	}

	public void setEditors(Set<ApplicationUser> editors) {
		this.editors = editors;
	}

	public void addEditor(ApplicationUser editor) {
		this.editors.add(editor);
	}

	public Set<ApplicationUser> getReviewers() {
		return reviewers;
	}

	public void setReviewers(Set<ApplicationUser> reviewers) {
		this.reviewers = reviewers;
	}

	public void addReviewer(ApplicationUser reviewer) {
		this.reviewers.add(reviewer);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
