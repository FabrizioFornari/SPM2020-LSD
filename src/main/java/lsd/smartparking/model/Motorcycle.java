package lsd.smartparking.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lsd.smartparking.enums.VehicleType;

@Document(collection = "vehicles")
public class Motorcycle extends Vehicle {
    
    private String cod;
	private String plate;
	

	public Motorcycle() { 
		this.setType(VehicleType.MOTORCYCLE);
	}

	@PersistenceConstructor
	public Motorcycle(ObjectId id, String name, String cod, String owner, String plate) {
		super(id, name, VehicleType.MOTORCYCLE, owner);
		this.cod = cod;
		this.plate = plate;
	}

	public Motorcycle(String name, String cod, String owner, String plate) {
		this(new ObjectId(), name, cod, owner, plate);
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

}