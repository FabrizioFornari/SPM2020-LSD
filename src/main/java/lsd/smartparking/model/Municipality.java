package lsd.smartparking.model;

import java.util.HashMap;

public class Municipality extends Utils {

	private String id;
	private String city;
	private String province;
	private String region;
	private String email;
	private HashMap<String, Policeman> policemen;
	private HashMap<String, HashMap<String, Double>> parking;
	private boolean approved;
	private boolean disabled;

	
    public Municipality() { }

	public Municipality(String city, String province, String region, String email, String id) {
		this.setId(id);
		this.setEmail(email);
		this.setCity(city);
		this.setProvince(province);
		this.setRegion(region);
		this.setApproved(false);
		this.setDisabled(false);
		this.setPolicemen(new HashMap<String, Policeman>());
		this.setParking(new HashMap<String, HashMap<String, Double>>());
	}

	public Municipality(Municipality municipality) {
		this.setId(municipality.getId());
		this.setEmail(municipality.getEmail());
		this.setCity(municipality.getCity());
		this.setProvince(municipality.getProvince());
		this.setRegion(municipality.getRegion());
		this.setApproved(false);
		this.setDisabled(false);
		this.setPolicemen(new HashMap<String, Policeman>());
		this.setParking(new HashMap<String, HashMap<String, Double>>());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		checkFields(id);
		this.id = id.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		checkFields(email);
		checkFieldsLength(email);
		this.email = email.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		checkFields(city);
		checkFieldsLength(city);
		this.city = city.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		checkFields(province);
		checkFieldsLength(province);
		this.province = province.trim();
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		checkFields(region);
		checkFieldsLength(region);
		this.region = region.trim();
	}

	public HashMap<String, Policeman> getPolicemen() {
		return policemen;
	}

	public void setPolicemen(HashMap<String, Policeman> policemen) {
		this.policemen = policemen;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public HashMap<String, HashMap<String, Double>> getParking() {
		return parking;
	}

	public void setParking(HashMap<String, HashMap<String, Double>> parking) {
		this.parking = parking;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
}