package lsd.smartparking.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

public class Auth<T extends Account> {

	@Size(min = 8)
	@NotBlank(message = "password cannot be empty")
    private String password;
	@Valid
	@NotNull(message = "Type cannot be null")
    private T user;


	public Auth(String password, T user) {
		Assert.isTrue(password.length() >= 8, "Password must be longer than 7 chars");
		Assert.hasText(password, "Password cannot be empty");
		Assert.notNull(user, "Type cannot be empty");
		this.password = password;
		this.user = user;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		Assert.isTrue(password.length() >= 8, "Password must be longer than 7 chars");
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