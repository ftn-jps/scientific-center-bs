package ftnjps.scientificcenter.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.EmailUtils;
import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.users.ApplicationUserService;

@Component
public class SendTerminationEmail implements JavaDelegate{

	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private ApplicationUserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ApplicationUser author = userService.findOne(
				Long.parseLong((String)execution.getVariable("authorId")));
		emailUtils.sendEmail(author.getEmail(),
				"Article submission terminated",
				"Your article submission has been terminated.");
	}

}
