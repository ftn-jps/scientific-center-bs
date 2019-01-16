package ftnjps.scientificcenter.users;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ftnjps.scientificcenter.fieldofstudy.FieldOfStudy;

@Entity
public class ApplicationUser {
	@Id
	@GeneratedValue
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@Version
	private Long version;

	@Email
	@NotBlank
	private String email;

	@Size(min = 8)
	@NotEmpty
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Pattern(regexp = "(?U)\\p{Alpha}*")
	@NotBlank
	private String name;

	@Pattern(regexp = "(?U)\\p{Alpha}*")
	@NotBlank
	private String lastName;

	@Pattern(regexp = "(?U)[\\p{Alpha}\\h]*")
	@NotBlank
	private String city;

	@Pattern(regexp = "(?U)[\\p{Alpha}\\h]*")
	@NotBlank
	private String country;

	@JsonProperty(access = Access.READ_ONLY)
	private String location;

	@Pattern(regexp = "(?U)[\\p{Alpha}\\h.]*")
	private String title;

	@ManyToMany
	private Set<FieldOfStudy> fieldsOfStudy = new HashSet<>();

	@Enumerated(EnumType.STRING)
	@JsonProperty(access = Access.READ_ONLY)
	private ApplicationUserType userType = ApplicationUserType.AUTHOR;

	@JsonIgnore
	private int failedLoginAttempts = 0;

	@JsonIgnore
	private String resetToken = null;

	public ApplicationUser() {}

	public ApplicationUser(String email,
			String password,
			String name,
			String lastName,
			String city,
			String country) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.city = city;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<FieldOfStudy> getFieldsOfStudy() {
		return fieldsOfStudy;
	}

	public void setFieldsOfStudy(Set<FieldOfStudy> fieldsOfStudy) {
		this.fieldsOfStudy = fieldsOfStudy;
	}

	public void addFieldOfStudy(FieldOfStudy field) {
		this.fieldsOfStudy.add(field);
	}

	public ApplicationUserType getUserType() {
		return userType;
	}

	public void setUserType(ApplicationUserType userType) {
		this.userType = userType;
	}

	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public void incrementFailedLoginAttempts() {
		++this.failedLoginAttempts;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public void generateResetToken() {
		this.resetToken = UUID.randomUUID().toString();
	}

}

