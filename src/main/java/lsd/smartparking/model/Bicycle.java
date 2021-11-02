package lsd.smartparking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import lsd.smartparking.enums.VehicleType;

@Document(collection = "vehicles")
public class Bicycle extends Vehicle {

	public Bicycle() { 
		this.setType(VehicleType.BYCICLE);
	}

	@PersistenceConstructor
	public Bicycle(ObjectId id, String name, String owner) {
		super(id, name, VehicleType.BYCICLE, owner);
	}
	
	public Bicycle(String name, String owner) {
		this(new ObjectId(), name, owner);
	}

	@JsonIgnore
	public String getPlate() {
		return null;
	}

}