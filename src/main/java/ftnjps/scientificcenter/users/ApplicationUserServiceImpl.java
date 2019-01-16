package ftnjps.scientificcenter.users;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Transactional(readOnly = true)
@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

	@Autowired
	private ApplicationUserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Value("${geocoding.url}")
	private String geocodingUrl;

	@Override
	public ApplicationUser findOne(Long id) {
		return userRepository.findById(id).orElse(null);
	}


	@Override
	public ApplicationUser findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public ApplicationUser findByResetToken(String token) {
		return userRepository.findByResetToken(token);
	}

	@Override
	public List<ApplicationUser> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public ApplicationUser add(ApplicationUser user) {
		ApplicationUser existing = findByEmail(user.getEmail());
		if(existing != null) // User already exists
			return null;

		if(user.getUserType() == ApplicationUserType.COAUTHOR) {
			user.setPassword(null);
		}
		else {
			if(!user.getPassword().matches("(?U)^(?=.*\\p{Lower})(?=.*\\p{Upper})(?=.*\\d).+$"))
				return null;
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}

		// Convert city and country to latitude and longitude
		// Uses Nominatim geocoder
		RestTemplate restClient = new RestTemplate();
		String query = null;
		try {
			query = geocodingUrl.replaceFirst("@1@",
					URLEncoder.encode(user.getCity(), "UTF-8"));
			query = query.replaceFirst("@2@",
					URLEncoder.encode(user.getCountry(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ResponseEntity<String> response = restClient.getForEntity(query, String.class);

		String location = response.getBody().replaceFirst(".*\"lat\":\"([\\d.-]+)\".*", "$1");
		location += "," + response.getBody().replaceFirst(".*\"lon\":\"([\\d.-]+)\".*", "$1");
		if(location.equals("[],[]"))
			return null;
		user.setLocation(location);

		return userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean changePassword(String email, ChangePasswordDTO passwordDto) {
		ApplicationUser user = findByEmail(email);
		if(!bCryptPasswordEncoder.matches(passwordDto.getOldPassword(), user.getPassword()))
			return false;

		user.setPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
		user = userRepository.save(user);
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public String generateResetToken(ApplicationUser forUser) {
		forUser.generateResetToken();
		userRepository.save(forUser);
		return forUser.getResetToken();
	}

	@Override
	@Transactional(readOnly = false)
	public String resetPassword(ApplicationUser forUser) {
		String newPassword = UUID.randomUUID().toString();
		forUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
		forUser.setResetToken(null);
		userRepository.save(forUser);
		return newPassword;
	}

	@Override
	@Transactional(readOnly = false)
	public void failedLogin(ApplicationUser user) {
		user.incrementFailedLoginAttempts();
		userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void successfulLogin(ApplicationUser user) {
		user.setFailedLoginAttempts(0);
		userRepository.save(user);
	}

}
