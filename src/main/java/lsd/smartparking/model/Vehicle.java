package lsd.smartparking.model;

import java.util.HashMap;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

@Document(collection = "vehicles")
public class Vehicle {
    
	@Id
	@NotNull(message = "Id cannot be null")
    private final ObjectId id;
	@NotBlank(message = "Name cannot be empty")
	private String name;
    private String type;
	@NotBlank(message = "Owner cannot be empty")
	private String owner;
	private HashMap<String, String> sharedOwners;
	

	public Vehicle() { 
		this.id = new ObjectId();
	}

	@PersistenceConstructor
	public Vehicle(ObjectId id, String name, String cod, String type, String owner, String plate) {
		Assert.isTrue(id.getClass() == ObjectId.class, "Id must be valid");
		Assert.hasText(type, "Type cannot be empty");
		Assert.hasText(type, "Name cannot be empty");
		Assert.hasText(type, "Owner cannot be empty");
		this.id =  id;
		this.type = type;
		this.name = name;
		this.owner = owner;
	}
    
	public String getId() {
		return this.id.toHexString();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		if (!type.equals("car") &&
			!type.equals("motorcycle") &&
			!type.equals("bicycle") &&
			!type.equals("caravan") &&
			!type.equals("handicap"))
			throw new IllegalArgumentException("Invalid vehicle type");
		this.type = type;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public HashMap<String, String> getSharedOwners() {
		return this.sharedOwners;
	}
	
	public void setSharedOwners(HashMap<String, String> owners) {
		this.sharedOwners = owners;
	}
	
	public void addOwner(String id, String name) {
		this.sharedOwners.put(id, name);
	}
	
	public void removeOwner(String id) {
		this.sharedOwners.remove(id);
	}

}