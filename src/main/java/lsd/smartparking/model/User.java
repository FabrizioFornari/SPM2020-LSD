package lsd.smartparking.model;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lsd.smartparking.enums.UserType;

@Document(collection = "users")
public class User extends Account {

	@NotBlank(message = "Name cannot be empty")
	private String name;
	@NotBlank(message = "Surname cannot be empty")
	private String surname;


	public User() { }

	public User(String email, String name, String surname, UserType type) {
		super(new ObjectId(), email, UserType.DRIVER);
		Assert.hasText(name, "Name cannot be empty");
		Assert.hasText(surname, "Surname cannot be empty");
		this.name = name;
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname.trim();
	}
	
}