package ftnjps.scientificcenter.users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class ChangePasswordDTO {
	@Size(min = 8)
	@NotEmpty
	@JsonProperty(access = Access.WRITE_ONLY)
	private String oldPassword;

	@Size(min = 8)
	@NotEmpty
	@JsonProperty(access = Access.WRITE_ONLY)
	private String newPassword;

	public ChangePasswordDTO() {}

	public ChangePasswordDTO(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


}
