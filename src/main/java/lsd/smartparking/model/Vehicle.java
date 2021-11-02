package lsd.smartparking.model;

import java.util.HashMap;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lsd.smartparking.enums.VehicleType;

@Document(collection = "vehicles")
public abstract class Vehicle {
    
	@Id
	@NotNull(message = "Id cannot be null")
    private final ObjectId id;
	@NotBlank(message = "Name cannot be empty")
	private String name;
	@NotNull(message = "Type cannot be null")
    private VehicleType type;
	@NotBlank(message = "Owner cannot be empty")
	private String owner;
	private HashMap<String, String> sharedOwners;
	

	public Vehicle() { 
		this.id = new ObjectId();
	}

	public Vehicle(ObjectId id, String name, VehicleType type, String owner) {
		Assert.isTrue(id.getClass() == ObjectId.class, "Id must be valid");
		Assert.hasText(name, "Name cannot be empty");
		Assert.notNull(type, "Type cannot be empty");
		Assert.hasText(owner, "Owner cannot be empty");
		this.id =  id;
		this.name = name;
		this.type = type;
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
	
	public VehicleType getType() {
		return type;
	}
	
	public void setType(VehicleType type) {
		Assert.notNull(type, "Type cannot be empty");
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

    public abstract String getPlate();

}