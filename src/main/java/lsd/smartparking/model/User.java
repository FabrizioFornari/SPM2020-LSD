package lsd.smartparking.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.google.cloud.firestore.annotation.DocumentId;

import org.springframework.util.Assert;

public class User extends Utils {

	@DocumentId
	@NotBlank(message = "Id cannot be empty")
	private String id;
	@NotBlank(message = "Name cannot be empty")
	private String name;
	@NotBlank(message = "Surname cannot be empty")
	private String surname;
	@NotBlank(message = "Email cannot be null")
	@Email(message = "Email must be valid")
	private String email;

	
	public User() { }

	public User(String id, String email, String name, String surname) {
		Assert.hasText(id, "Id cannot be empty");
		Assert.hasText(name, "Name cannot be empty");
		Assert.hasText(surname, "Surname cannot be empty");
		Assert.hasText(email, "Email cannot be empty");
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}

	public String getId() {
		return id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		checkFields(email);
		this.email = email.trim();
	}
	
}