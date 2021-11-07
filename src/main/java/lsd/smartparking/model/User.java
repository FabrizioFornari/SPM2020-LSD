package lsd.smartparking.model;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
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

	@PersistenceConstructor
	public User(ObjectId id, String email, String name, String surname, UserType type) {
		super(id, email, type);
		Assert.hasText(name, "Name cannot be empty");
		Assert.hasText(surname, "Surname cannot be empty");
		this.name = name;
		this.surname = surname;
	}

	public User(String email, String name, String surname, UserType type) {
		this(new ObjectId(), email, name, surname, type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Assert.hasText(name, "Name cannot be empty");
		this.name = name.trim();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		Assert.hasText(surname, "Surname cannot be empty");
		this.surname = surname.trim();
	}
	
}