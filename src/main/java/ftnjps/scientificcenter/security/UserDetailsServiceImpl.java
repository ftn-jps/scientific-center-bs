package ftnjps.scientificcenter.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ftnjps.scientificcenter.users.ApplicationUser;
import ftnjps.scientificcenter.users.ApplicationUserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private ApplicationUserService applicationUserService;
//	private PrivilegeService privilegeService;

	public UserDetailsServiceImpl(ApplicationUserService applicationUserService
			/*, PrivilegeService privilegeService */) {
		this.applicationUserService = applicationUserService;
//		this.privilegeService = privilegeService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser applicationUser = applicationUserService.findByEmail(username);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(username);
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(applicationUser.getUserType().toString()));
//		List<Privilege> privileges = privilegeService.findAllForRole(applicationUser.getUserType());
//		for(Privilege privilege : privileges) {
//			authorities.add(new SimpleGrantedAuthority(privilege.getName()));
//		}

		return new User(applicationUser.getEmail(),
				applicationUser.getPassword(),
				applicationUser.getFailedLoginAttempts() < SecurityConstants.MAX_FAILED_ATTEMPTS,
				true,
				true,
				true,
				authorities);
	}

}