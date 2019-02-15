package ftnjps.scientificcenter.camunda;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.form.FormField;

import ftnjps.scientificcenter.fieldofstudy.FieldOfStudy;
import ftnjps.scientificcenter.users.ApplicationUser;

public class TaskDetailDto {

	private String id;
	private String name;
	private String journalName;

	private String title;
	private String keywords;
	private String articleAbstract;
	private String fieldOfStudyId;
	private List<ApplicationUser> coauthors;

	private String pdfContent;
	private List<FormField> formFields = new ArrayList<>();
	private List<FieldOfStudy> fieldsOfStudy;

	public TaskDetailDto() {}
	public TaskDetailDto(String id,
			String name,
			String journalName,
			String title,
			String keywords,
			String articleAbstract,
			String fieldOfStudyId,
			List<ApplicationUser> coauthors,
			List<FormField> formFields) {
		super();
		this.id = id;
		this.name = name;
		this.journalName = journalName;
		this.title = title;
		this.keywords = keywords;
		this.articleAbstract = articleAbstract;
		this.fieldOfStudyId = fieldOfStudyId;
		this.coauthors = coauthors;
		this.formFields = formFields;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getFieldOfStudyId() {
		return fieldOfStudyId;
	}
	public void setFieldOfStudyId(String fieldOfStudyId) {
		this.fieldOfStudyId = fieldOfStudyId;
	}
	public List<ApplicationUser> getCoauthors() {
		return coauthors;
	}
	public void setCoauthors(List<ApplicationUser> coauthors) {
		this.coauthors = coauthors;
	}
	public String getPdfContent() {
		return pdfContent;
	}
	public void setPdfContent(String pdfContent) {
		this.pdfContent = pdfContent;
	}
	public List<FormField> getFormFields() {
		return formFields;
	}
	public void setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
	}
	public List<FieldOfStudy> getFieldsOfStudy() {
		return fieldsOfStudy;
	}
	public void setFieldsOfStudy(List<FieldOfStudy> fieldsOfStudy) {
		this.fieldsOfStudy = fieldsOfStudy;
	}

}
