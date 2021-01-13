package lsd.smartparking.model;

import java.util.HashMap;

public class Parking {

	private String name;
	private String id;
	private double lat;
	private double lon;
	private double price;
	private String municipalityId;
	private boolean guarded;
	private HashMap<Integer, Day> days;
	private HashMap<String, Integer> slots;
	
	
	public Parking() { }

	public Parking(String id, String name, double lat, double lon, double price, boolean guarded, String municipalityId, HashMap<String, Integer> slots) {
		this.setId(id);
		this.setName(name);
		this.setLat(lat);
		this.setLon(lon);
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
		if (!slots.isEmpty()) throw new IllegalArgumentException("Invalid parking slots");
		this.slots = slots;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if (price <= 0) throw new IllegalArgumentException("Invalid parking price");
		this.price = price;
	}

}
