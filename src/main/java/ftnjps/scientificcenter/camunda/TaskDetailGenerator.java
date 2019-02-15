package ftnjps.scientificcenter.camunda;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.fieldofstudy.FieldOfStudyService;
import ftnjps.scientificcenter.users.ApplicationUser;

@Component
public class TaskDetailGenerator {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private FormService formService;
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
				formFields);
		result.setFieldsOfStudy(fieldOfStudyService.findAll());
		Map<String, Object> pdfContent = (Map<String, Object>)
				runtimeService.getVariable(task.getProcessInstanceId(), "pdfContent");
		if(pdfContent != null)
			result.setPdfContent((String) pdfContent.get("base64"));
		return result;
	}

}
