package lsd.smartparking.model;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

public class Auth<T extends UserInfo> {

	@Email(message = "Email must be valid")
	@NotBlank(message = "Email cannot be empty")
	private String email;
	@NotBlank(message = "password cannot be empty")
    private String password;
	@Valid
	@NotNull(message = "Type cannot be null")
    private T user;


	public Auth(String email, String password, T user) {
		Assert.hasText(email, "Email cannot be empty");
		Assert.hasText(password, "Password cannot be empty");
		Assert.notNull(user, "Type cannot be empty");
		this.email = email;
		this.password = password;
		this.user = user;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		Assert.hasText(email, "Email cannot be empty");
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		Assert.hasText(password, "Password cannot be empty");
		this.password = password;
	}

	public T getUser() {
		return this.user;
	}

	public void setType(T user) {
		Assert.notNull(user, "Type cannot be empty");
		this.user = user;
	}
    
}
