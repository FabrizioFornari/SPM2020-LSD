package lsd.smartparking.model;

import java.util.HashMap;

public class Municipality {

	private String id;
	private String city;
	private String province;
	private String region;
	private String email;
	private HashMap<String, Policeman> policemen;

	
    public Municipality() { }

	public Municipality(String city, String province, String region, String id, String email) {
		this.id = id;
		this.email = email;
		this.city = city;
		this.province = province;
		this.region = region;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public HashMap<String, Policeman> getPolicemen() {
		return policemen;
	}

	public void setPolicemen(HashMap<String, Policeman> policemen) {
		this.policemen = policemen;
	}
	
}