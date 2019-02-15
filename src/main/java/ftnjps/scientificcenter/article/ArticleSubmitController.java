package ftnjps.scientificcenter.article;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftnjps.scientificcenter.camunda.TaskDetailDto;
import ftnjps.scientificcenter.camunda.TaskDto;
import ftnjps.scientificcenter.fieldofstudy.FieldOfStudyService;
import ftnjps.scientificcenter.journal.Journal;
import ftnjps.scientificcenter.journal.JournalService;
import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.users.ApplicationUserService;

@RestController
@RequestMapping("/api/article-submit")
public class ArticleSubmitController {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;

	@Autowired
	private JournalService journalService;
	@Autowired
	private ApplicationUserService userService;
	@Autowired
	private FieldOfStudyService fieldOfStudyService;

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/{journalId}")
	public ResponseEntity<?> startSubmission(Principal principal,
			@PathVariable Long journalId) {
		Journal journal = journalService.findOne(journalId);
		if(journal == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ApplicationUser author = userService.findByEmail(principal.getName());

		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("journalId", journal.getId());
		variables.put("journalName", journal.getName());
		variables.put("authorId", author.getId().toString());
		runtimeService.startProcessInstanceByKey("submission", variables);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<?> getAllTasks(Principal principal) {
		ApplicationUser author = userService.findByEmail(principal.getName());

		List<Task> tasks = taskService.createTaskQuery()
			.active()
			.taskAssignee(author.getId().toString())
			.list();

		List<TaskDto> result = new ArrayList<>();
		for(Task task : tasks) {
			result.add(new TaskDto(
					task.getId(),
					task.getName(),
					(String)runtimeService.getVariable(task.getProcessInstanceId(), "journalName"),
					(String)runtimeService.getVariable(task.getProcessInstanceId(), "articleTitle")));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{taskId}")
	public ResponseEntity<?> getTask(Principal principal,
			@PathVariable String taskId) {
		ApplicationUser author = userService.findByEmail(principal.getName());

		Task task = taskService.createTaskQuery()
			.taskId(taskId)
			.active()
			.taskAssignee(author.getId().toString())
			.list().get(0);
		if(task == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<FormField> formFields = formService.getTaskFormData(taskId).getFormFields();

		TaskDetailDto result = new TaskDetailDto(
				task.getId(),
				task.getName(),
				(String)runtimeService.getVariable(task.getProcessInstanceId(), "journalName"),
				(Article)runtimeService.getVariable(task.getProcessInstanceId(), "article"),
				(String)runtimeService.getVariable(task.getProcessInstanceId(), "pdfContent"),
				formFields);
		result.setFieldsOfStudy(fieldOfStudyService.findAll());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/task/{taskId}")
	public ResponseEntity<?> submitTask(Principal principal,
			@PathVariable String taskId,
			@RequestBody Map<String, Object> form) {
		ApplicationUser author = userService.findByEmail(principal.getName());

		Task task = taskService.createTaskQuery()
			.taskId(taskId)
			.active()
			.taskAssignee(author.getId().toString())
			.list().get(0);
		if(task == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		runtimeService.setVariables(task.getProcessInstanceId(), form);
		taskService.complete(taskId);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}