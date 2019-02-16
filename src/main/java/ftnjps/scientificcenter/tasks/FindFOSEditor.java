package ftnjps.scientificcenter.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.fieldofstudy.FieldOfStudy;
import ftnjps.scientificcenter.fieldofstudy.FieldOfStudyService;
import ftnjps.scientificcenter.journal.Journal;
import ftnjps.scientificcenter.journal.JournalService;
import ftnjps.scientificcenter.users.ApplicationUser;

@Component
public class FindFOSEditor implements JavaDelegate{

	@Autowired
	JournalService journalService;
	@Autowired
	private FieldOfStudyService fieldOfStudyService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Journal journal = journalService.findOne(
				Long.parseLong((String)execution.getVariable("journalId")));
		FieldOfStudy fieldOfStudy = fieldOfStudyService.findOne(
				Long.parseLong((String)execution.getVariable("fieldOfStudyId")));

		boolean reviewerExists = false;
		for(ApplicationUser reviewer : journal.getReviewers()) {
			if(reviewer.getFieldsOfStudy().contains(fieldOfStudy)) {
				reviewerExists = true;
				break;
			}
		}
		if(!reviewerExists) {
			execution.setVariable("otherEditorId", execution.getVariable("mainEditorId"));
			return;
		}

		for(ApplicationUser editor : journal.getEditors()) {
			if(editor.getFieldsOfStudy().contains(fieldOfStudy)) {
				execution.setVariable("otherEditorId", editor.getId().toString());
				return;
			}
		}
		execution.setVariable("otherEditorId", execution.getVariable("mainEditorId"));
	}

}
