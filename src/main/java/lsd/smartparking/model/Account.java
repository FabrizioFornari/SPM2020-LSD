package lsd.smartparking.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

import lsd.smartparking.enums.UserType;

public class Account {

	@Id
	@NotNull(message = "Id must be valid")
	private final ObjectId id;
	@Email(message = "Email must be valid")
	@NotBlank(message = "Email cannot be null")
	private String email;
	@NotNull(message = "Type cannot be empty")
	private UserType type;

	
	public Account() { 
		this.id = new ObjectId();
	}

	public Account(ObjectId id, String email, UserType type) {
		Assert.notNull(id, "Id must be valid");
		Assert.hasText(email, "Email cannot be empty");
		Assert.notNull(type, "Type cannot be empty");
		this.id = id;
		this.email = email;
		this.type = type;
	}

	public String getId() {
		return this.id.toHexString();
	}	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public UserType getType() {
		return this.type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
}