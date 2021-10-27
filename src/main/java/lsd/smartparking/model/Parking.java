package lsd.smartparking.model;

import java.util.HashMap;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

@Document(collection = "parkings")
public class Parking {

	@Id
	@NotNull(message = "Id cannot be null")
	private final ObjectId id;
	@NotBlank(message = "Name cannot be empty")
	private String name;
	@Valid
	@NotNull(message = "Coords cannot be empty")
	@GeoSpatialIndexed(name="coords")
	private Coords coords;
	@NotBlank(message = "Address cannot be empty")
	private String address;
	@NotBlank(message = "City cannot be empty")
	private String city;
	@NotBlank(message = "Owner cannot be empty")
	private String owner;
	@Min(0)
	private double price;
	private boolean guarded;
	private HashMap<Integer, Day> days;
	private HashMap<String, Integer> slots;
	

	public Parking() {
		this.id = new ObjectId();
	}

	@PersistenceConstructor
	public Parking(ObjectId id, String name, Coords coords, String address, String city, String owner, double price) {
		Assert.isTrue(id.getClass() == ObjectId.class, "Id must be valid");
		Assert.hasText(name, "Name cannot be empty");
		Assert.notNull(coords, "Coords cannot be null");
		Assert.notNull(address, "Address cannot be empty");
		Assert.hasText(city, "City cannot be empty");
		Assert.hasText(owner, "Owner cannot be empty");
		Assert.isTrue(price >= 0, "Price cannot be negative");
		this.id = id;
		this.name = name;
		this.coords = coords;
		this.address = address;
		this.city = city;
		this.owner = owner;
		this.price = price;
		this.days = new HashMap<Integer, Day>();
	}

	public Parking(String name, Coords coords, String address, String city, String owner, double price) {
		this(new ObjectId(), name, coords, address, city, owner, price);
	}

	public Parking(String name, Coords coords, String address, String city, String owner, double price, HashMap<String, Integer> slots, boolean guarded) {
		this(new ObjectId(), name, coords, address, city, owner, price);
		this.slots = slots;
		this.guarded = guarded;
	}

	public String getId() {
		return id.toHexString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Assert.hasText(name, "Name cannot be empty");
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public Coords getCoords() {
		return coords;
	}

	public void setCoords(Coords coords) {
		Assert.notNull(coords, "Coords cannot be empty");
		this.coords = coords;
	}

	public HashMap<Integer, Day> getDays() {
		return days;
	}

	public void setDays(HashMap<Integer, Day> days) {
		this.days = days;
	}

	public boolean isGuarded() {
		return guarded;
	}

	public void setGuarded(boolean guarded) {
		this.guarded = guarded;
	}

	public HashMap<String, Integer> getSlots() {
		return slots;
	}

	public void setSlots(HashMap<String, Integer> slots) {
		Assert.notEmpty(slots, "Slots cannot be empty");
		for (String vehicleType : slots.keySet()) {
			if (!vehicleType.equals("car") &&
				!vehicleType.equals("motorcycle") &&
				!vehicleType.equals("bicycle") &&
				!vehicleType.equals("caravan") &&
				!vehicleType.equals("handicap")) {
					throw new IllegalArgumentException("Invalid vehicle type");
			}
		}
		this.slots = slots;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		Assert.isTrue(price >= 0, "Price cannot be negative");
		this.price = price;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}