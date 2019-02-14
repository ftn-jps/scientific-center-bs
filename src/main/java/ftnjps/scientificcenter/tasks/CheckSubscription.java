package ftnjps.scientificcenter.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CheckSubscription implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("isOpenAccess", false);
	}

}
