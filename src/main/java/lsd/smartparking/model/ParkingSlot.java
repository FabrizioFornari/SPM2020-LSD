package lsd.smartparking.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

@Document(collection = "parkings")
public class ParkingSlot {

	@Id
	@NotNull(message = "Id cannot be null")
	private final ObjectId id;
	@NotBlank(message = "Parking cannot be empty")
	private String parking;
	@Valid
	@NotNull(message = "Coords cannot be empty")
	@GeoSpatialIndexed(name="coords")
	private Coords coords;
	

	public ParkingSlot() {
		this.id = new ObjectId();
	}

	@PersistenceConstructor
	public ParkingSlot(ObjectId id, String parking, Coords coords) {
		Assert.isTrue(id.getClass() == ObjectId.class, "Id must be valid");
		Assert.hasText(parking, "Parking cannot be empty");
		Assert.notNull(coords, "Coords cannot be null");
		this.id = id;
		this.parking = parking;
		this.coords = coords;
	}

	public ParkingSlot(String parking, Coords coords) {
		this(new ObjectId(), parking, coords);
	}

	public String getId() {
		return id.toHexString();
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		Assert.hasText(parking, "Parking cannot be empty");
		this.parking = parking;
	}

	public Coords getCoords() {
		return coords;
	}

	public void setCoords(Coords coords) {
		Assert.notNull(coords, "Coords cannot be empty");
		this.coords = coords;
	}

}