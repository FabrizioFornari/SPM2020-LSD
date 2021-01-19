package lsd.smartparking.model;

import java.util.HashMap;
import java.util.UUID;

public class Parking {

	private String name;
	private String id;
	private double lat;
	private double lon;
	private double price;
	private String municipalityId;
	private String city;
	private String address;
	private boolean guarded;
	private HashMap<Integer, Day> days;
	private HashMap<String, Integer> slots;
	
	
	public Parking() { 
		this.setId(UUID.randomUUID().toString());
	}

	public Parking(String name, String city, String address, double lat, double lon, double price, boolean guarded, String municipalityId, HashMap<String, Integer> slots) {
		this.setId(UUID.randomUUID().toString());
		this.setName(name);
		this.setLat(lat);
		this.setLon(lon);
		this.setCity(city);
		this.setAddress(address);
		this.setPrice(price);
		this.setMunicipalityId(municipalityId);
		this.setDays(new HashMap<Integer, Day>());
		this.setSlots(slots);
		this.setGuarded(guarded);
	}

	public String getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(String municipalityId) {
		this.municipalityId = municipalityId;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		if (slots.isEmpty()) throw new IllegalArgumentException("Invalid parking slots");
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
