package ftnjps.scientificcenter.camunda;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.form.FormField;

import ftnjps.scientificcenter.article.Article;
import ftnjps.scientificcenter.fieldofstudy.FieldOfStudy;

public class TaskDetailDto {

	private String id;
	private String name;
	private String journalName;
	private Article article;
	private String pdfContent;
	private List<FormField> formFields = new ArrayList<>();
	private List<FieldOfStudy> fieldsOfStudy;

	public TaskDetailDto() {}
	public TaskDetailDto(String id,
			String name,
			String journalName,
			Article article,
			String pdfContent,
			List<FormField> formFields) {
		this.id = id;
		this.name = name;
		this.journalName = journalName;
		this.article = article;
		this.pdfContent = pdfContent;
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
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
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
