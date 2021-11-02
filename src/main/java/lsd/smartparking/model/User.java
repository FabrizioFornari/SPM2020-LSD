package lsd.smartparking.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lsd.smartparking.enums.UserType;

@Document(collection = "users")
public class User extends Utils implements UserInfo {

	@Id
	@NotNull(message = "Id must be valid")
	private final ObjectId id;
	@Email(message = "Email must be valid")
	@NotBlank(message = "Email cannot be null")
	private String email;
	@NotBlank(message = "Name cannot be empty")
	private String name;
	@NotBlank(message = "Surname cannot be empty")
	private String surname;
	@NotNull(message = "type cannot be empty")
	private UserType type;

	
	public User() { 
		this.id = new ObjectId();
	}

	public User(ObjectId id, String email, String name, String surname, UserType type) {
		Assert.notNull(id, "Id must be valid");
		Assert.hasText(email, "Email cannot be empty");
		Assert.hasText(name, "Name cannot be empty");
		Assert.hasText(surname, "Surname cannot be empty");
		Assert.notNull(type, "Type cannot be empty");
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.type = type;
	}

	@Override
	public String getId() {
		return this.id.toHexString();
	}	

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		checkFields(email);
		this.email = email.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		checkFields(name);
		checkFieldsLength(name, 30);
		this.name = name.trim();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		checkFields(surname);
		checkFieldsLength(surname, 30);
		this.surname = surname.trim();
	}

	@Override
	public UserType getType() {
		return this.type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
}