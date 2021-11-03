package lsd.smartparking.model;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lsd.smartparking.enums.VehicleType;

@Document(collection = "vehicles")
public class Motorcycle extends Vehicle {
    
	@NotBlank(message = "Cod cannot be empty")
    private String cod;
	@NotBlank(message = "plate cannot be empty")
	private String plate;
	

	public Motorcycle() { 
		this.setType(VehicleType.MOTORCYCLE);
	}

	@PersistenceConstructor
	public Motorcycle(ObjectId id, String name, String cod, String owner, String plate) {
		super(id, name, VehicleType.MOTORCYCLE, owner);
		Assert.hasText(cod, "Cod cannot be empty");
		Assert.hasText(plate, "Plate cannot be empty");
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
		Assert.hasText(plate, "Plate cannot be empty");
		this.plate = plate;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		Assert.hasText(cod, "Cod cannot be empty");
		this.cod = cod;
	}

}