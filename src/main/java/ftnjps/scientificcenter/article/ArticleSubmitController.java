package ftnjps.scientificcenter.article;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
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

import ftnjps.scientificcenter.camunda.TaskDetailGenerator;
import ftnjps.scientificcenter.camunda.TaskDto;
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
	private TaskDetailGenerator taskDetailGenerator;

	@Autowired
	private JournalService journalService;
	@Autowired
	private ApplicationUserService userService;

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
		variables.put("journalId", journal.getId().toString());
		variables.put("journalName", journal.getName());
		variables.put("mainEditorId", journal.getMainEditor().getId().toString());
		variables.put("authorId", author.getId().toString());
		variables.put("isRelevant", false);
		variables.put("isBadFormatting", false);
		variables.put("reviews", new ArrayList<Map<String, Object>>());
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
					(String)runtimeService.getVariable(task.getProcessInstanceId(), "title")));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{taskId}")
	public ResponseEntity<?> getTask(Principal principal,
			@PathVariable String taskId) {
		ApplicationUser author = userService.findByEmail(principal.getName());

		Task task;
		try {
			task = taskService.createTaskQuery()
				.taskId(taskId)
				.active()
				.taskAssignee(author.getId().toString())
				.list().get(0);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(taskDetailGenerator.generate(task), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
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

		if(form.get("review") != null) {
			List<Map<String, Object>> reviews =
					(List<Map<String, Object>>)runtimeService.getVariable(task.getProcessInstanceId(), "reviews");
			reviews.add((Map<String, Object>) form.get("review"));
			runtimeService.setVariable(task.getProcessInstanceId(), "reviews", reviews);
		}
		runtimeService.setVariables(task.getProcessInstanceId(), form);
		taskService.complete(taskId);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}