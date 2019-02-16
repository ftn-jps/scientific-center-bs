package ftnjps.scientificcenter.camunda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.fieldofstudy.FieldOfStudyService;
import ftnjps.scientificcenter.journal.Journal;
import ftnjps.scientificcenter.journal.JournalService;
import ftnjps.scientificcenter.users.ApplicationUser;

@Component
public class TaskDetailGenerator {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private FormService formService;
	@Autowired
	private JournalService journalService;
	@Autowired
	private FieldOfStudyService fieldOfStudyService;

	@SuppressWarnings("unchecked")
	public TaskDetailDto generate(Task task) {
		List<FormField> formFields = formService.getTaskFormData(task.getId()).getFormFields();
		TaskDetailDto result = new TaskDetailDto(
				task.getId(),
				task.getName(),
				(String)runtimeService.getVariable(task.getProcessInstanceId(), "journalName"),
				(String)runtimeService.getVariable(task.getProcessInstanceId(), "title"),
				(String)runtimeService.getVariable(task.getProcessInstanceId(), "keywords"),
				(String)runtimeService.getVariable(task.getProcessInstanceId(), "articleAbstract"),
				(String)runtimeService.getVariable(task.getProcessInstanceId(), "fieldOfStudyId"),
				(List<ApplicationUser>)runtimeService.getVariable(task.getProcessInstanceId(), "coauthors"),
				formFields,
				(String)runtimeService.getVariable(task.getProcessInstanceId(), "mainEditorComment"),
				(String)runtimeService.getVariable(task.getProcessInstanceId(), "authorComment"));

		result.setFieldsOfStudy(fieldOfStudyService.findAll());

		Journal journal = journalService.findOne(
				Long.parseLong((String)runtimeService.getVariable(task.getProcessInstanceId(), "journalId")));
		result.setReviewers(new ArrayList<>(journal.getReviewers()));

		List<Map<String, Object>> reviews = (List<Map<String, Object>>)
				runtimeService.getVariable(task.getProcessInstanceId(), "reviews");
		result.setReviews(reviews);

		Map<String, Object> pdfContent = (Map<String, Object>)
				runtimeService.getVariable(task.getProcessInstanceId(), "pdfContent");
		if(pdfContent != null)
			result.setPdfContent((String) pdfContent.get("base64"));

		return result;
	}

}