package lsd.smartparking.model;

import java.util.HashMap;

public class Parking {

	private String name;
	private String id;
	private double lat;
	private double lon;
	private String municipalityId;
	private boolean guarded;
	private HashMap<Integer, Day> days;
	
	
	public Parking() { }

	public Parking(String id, String name, double lat, double lon, boolean guarded, String municipalityId) {
		this.setId(id);
		this.setName(name);
		this.setLat(lat);
		this.setLon(lon);
		this.setMunicipalityId(municipalityId);
		this.setDays(new HashMap<Integer, Day>());
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

}
