package lsd.smartparking.model;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

public class ParkingInfo {

	@Id
	@NotNull(message = "Id cannot be null")
	private final ObjectId id;
	@Valid
	@NotNull(message = "Coords cannot be empty")
	private final Coords coords;
	@Min(0)
	private final double price;


    @PersistenceConstructor
    public ParkingInfo(ObjectId id, Coords coords, double price) {
        this.id = id;
        this.coords = coords;
        this.price = price;
    }

    public String getId() {
        return this.id.toHexString();
    }

    public Coords getCoords() {
        return this.coords;
    }

    public double getPrice() {
        return this.price;
    }
    
}