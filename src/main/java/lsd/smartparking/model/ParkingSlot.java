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

import lsd.smartparking.enums.VehicleType;

@Document(collection = "slots")
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
	@Valid
	private VehicleType type;
	

	public ParkingSlot() {
		this.id = new ObjectId();
	}

	@PersistenceConstructor
	public ParkingSlot(ObjectId id, String parking, Coords coords, VehicleType type) {
		Assert.isTrue(id.getClass() == ObjectId.class, "Id must be valid");
		Assert.hasText(parking, "Parking cannot be empty");
		Assert.notNull(coords, "Coords cannot be null");
		this.id = id;
		this.parking = parking;
		this.coords = coords;
		this.type = type;
	}

	public ParkingSlot(String parking, Coords coords, VehicleType type) {
		this(new ObjectId(), parking, coords, type);
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

	public void setCoords(@Valid Coords coords) {
		Assert.notNull(coords, "Coords cannot be empty");
		this.coords = coords;
	}

	public VehicleType getType() {
		return this.type;
	}

	public void setType(@Valid VehicleType type) {
		Assert.notNull(type, "Type cannot be empty");
		this.type = type;
	}

}