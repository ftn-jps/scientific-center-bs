package ftnjps.scientificcenter.users;

import java.util.List;

public interface ApplicationUserService {

	ApplicationUser findOne(Long id);

	ApplicationUser findByEmail(String email);

	ApplicationUser findByResetToken(String token);

	List<ApplicationUser> findAll();

	ApplicationUser add(ApplicationUser user);

	boolean changePassword(String email, ChangePasswordDTO passwordDto);

	String generateResetToken(ApplicationUser forUser);

	String resetPassword(ApplicationUser forUser);

	void failedLogin(ApplicationUser user);

	void successfulLogin(ApplicationUser user);

}
