package lsd.smartparking.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import lsd.smartparking.enums.UserType;

@Document(collection = "users")
public class Driver extends User {

	public Driver() { 
		this.setType(UserType.DRIVER);
	}
	
	@PersistenceConstructor
	public Driver(ObjectId id, String email, String name, String surname) {
		super(id, email, name, surname, UserType.DRIVER);
	}

	public Driver(String email, String name, String surname) {
		this(new ObjectId(), email, name, surname);
	}

}