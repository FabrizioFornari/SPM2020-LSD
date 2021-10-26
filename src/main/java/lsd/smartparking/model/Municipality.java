package lsd.smartparking.model;

import java.util.HashMap;

public class Municipality extends Utils {

	private String id;
	private String email;
	private String city;
	private String province;
	private String region;
	private HashMap<String, Policeman> policemen;
	private HashMap<String, HashMap<String, Double>> parkings;
	private boolean approved = false;
	private boolean disabled = false;

	
    public Municipality() { }

	public Municipality(String id, String email, String city, String province, String region) {
		this.id = id;
		this.email = email;
		this.city = city;
		this.province = province;
		this.region = region;
		this.policemen = new HashMap<String, Policeman>();
		this.parkings = new HashMap<String, HashMap<String, Double>>();
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
		this.email = email.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		checkFields(city);
		checkFieldsLength(city, 30);
		this.city = city.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		checkFields(province);
		checkFieldsLength(province, 30);
		this.province = province.trim();
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		checkFields(region);
		checkFieldsLength(region, 30);
		this.region = region.trim();
	}

	public HashMap<String, Policeman> getPolicemen() {
		return policemen;
	}

	public void setPolicemen(HashMap<String, Policeman> policemen) {
		this.policemen = policemen;
	}

	public HashMap<String, HashMap<String, Double>> getParkings() {
		return parkings;
	}

	public void setParkings(HashMap<String, HashMap<String, Double>> parkings) {
		this.parkings = parkings;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
}