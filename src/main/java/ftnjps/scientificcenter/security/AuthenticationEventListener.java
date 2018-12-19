package ftnjps.scientificcenter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.users.ApplicationUserService;

@Component
public class AuthenticationEventListener {

	@Autowired
	private ApplicationUserService applicationUserService;

	@EventListener
	public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {
		String email = (String) event.getAuthentication().getPrincipal();
		ApplicationUser user = applicationUserService.findByEmail(email);
		applicationUserService.failedLogin(user);
	}

	@EventListener
	public void authenticationSucceeded(AuthenticationSuccessEvent event) {
		User principal = (User) event.getAuthentication().getPrincipal();
		String email = principal.getUsername();
		ApplicationUser user = applicationUserService.findByEmail(email);
		applicationUserService.successfulLogin(user);
	}

}
