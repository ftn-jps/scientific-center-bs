package ftnjps.scientificcenter.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class GatewayRedirect implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Redirect to Payment Gateway
	}

}
