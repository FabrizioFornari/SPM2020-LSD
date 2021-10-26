package lsd.smartparking.model;

import java.util.HashMap;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.geo.Point;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

@Document(collection = "parkings")
public class Parking {

	private final ObjectId _id;
	private String name;
	private Point coords;
	private String address;
	private String city;
	private String municipalityId;
	private double price;
	private boolean guarded;
	private HashMap<Integer, Day> days;
	private HashMap<String, Integer> slots;
	

	public Parking(String name, double lat, double lon, String address, String city, String municipalityId, double price) {
		this._id = new ObjectId();
		this.name = name;
		this.coords = new Point(lon, lat);
		this.address = address;
		this.city = city;
		this.municipalityId = municipalityId;
		this.price = price;
		this.days = new HashMap<Integer, Day>();
	}

	public Parking(String name, double lat, double lon, String address, String city, String municipalityId, double price, HashMap<String, Integer> slots, boolean guarded) {
		this(name, lat, lon, address, city, municipalityId, price);
		this.slots = slots;
		this.guarded = guarded;
	}

	public String getId() {
		return _id.toHexString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMunicipalityId() {
		return municipalityId;
	}

	public double getLon() {
		return coords.getX();
	}

	public double getLat() {
		return coords.getY();
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
		if (price < 0) price = 0;
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
