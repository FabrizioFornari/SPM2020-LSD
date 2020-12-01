package lsd.smartparking.model;

public class Parking {

	private String name;
	private String id;
	private double lat;
	private double lon;
	private String municipalityId;
	
	
	public Parking() { }

	public Parking(String id, String name, double lat, double lon, String municipalityId) {
		this.setId(id);
		this.setName(name);
		this.setLat(lat);
		this.setLon(lon);
		this.setMunicipalityId(municipalityId);
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

}
