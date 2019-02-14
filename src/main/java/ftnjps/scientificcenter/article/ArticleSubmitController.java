package ftnjps.scientificcenter.article;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	TaskService taskService;
	@Autowired
	FormService formService;

	@Autowired
	JournalService journalService;
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
					(String)runtimeService.getVariable(task.getProcessInstanceId(), "articleName")));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}