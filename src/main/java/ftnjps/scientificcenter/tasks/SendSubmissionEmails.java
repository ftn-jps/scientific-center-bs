package ftnjps.scientificcenter.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.EmailUtils;
import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.users.ApplicationUserService;

@Component
public class SendSubmissionEmails implements JavaDelegate{

	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private ApplicationUserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ApplicationUser author = userService.findOne(
				Long.parseLong((String)execution.getVariable("authorId")));
		ApplicationUser mainEditor = userService.findOne(
				Long.parseLong((String)execution.getVariable("mainEditorId")));
		emailUtils.sendEmail(author.getEmail(),
				"New article submitted",
				"Your article has been submitted. Please wait for a review.");
		emailUtils.sendEmail(mainEditor.getEmail(),
				"New article submitted",
				"New article has been submitted, login to Scientific Center to view it.");
	}

}
