package ftnjps.scientificcenter.tasks;

import java.util.HashSet;
import java.util.Set;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.article.Article;
import ftnjps.scientificcenter.article.ArticleService;
import ftnjps.scientificcenter.camunda.TaskDetailDto;
import ftnjps.scientificcenter.camunda.TaskDetailGenerator;
import ftnjps.scientificcenter.journal.Journal;
import ftnjps.scientificcenter.journal.JournalService;
import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.users.ApplicationUserService;
import ftnjps.scientificcenter.users.ApplicationUserType;

@Component
public class IndexArticle implements JavaDelegate{

	@Autowired
	private ArticleService articleService;
	@Autowired
	private JournalService journalService;
	@Autowired
	private ApplicationUserService userService;
	@Autowired
	private TaskDetailGenerator taskDetailGenerator;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Task task = taskService.createTaskQuery().executionId(execution.getId()).list().get(0);
		TaskDetailDto taskDto = taskDetailGenerator.generate(task);

		Journal journal = journalService.findOne(
				Long.parseLong((String)runtimeService.getVariable(task.getProcessInstanceId(), "journalId")));

		Article article = new Article(taskDto.getTitle(),
				taskDto.getAuthor(),
				journal,
				taskDto.getKeywords(),
				taskDto.getArticleAbstract(),
				taskDto.getFieldOfStudy(),
				null);
		if(taskDto.getCoauthors() != null) {
			Set<ApplicationUser> coauthors = new HashSet<>();
			for(ApplicationUser coauthor : taskDto.getCoauthors()) {
				coauthor.setUserType(ApplicationUserType.COAUTHOR);
				coauthors.add(userService.add(coauthor));
			}
			article.setCoauthors(coauthors);
		}
		articleService.add(article, taskDto.getPdfContent());
	}

}
