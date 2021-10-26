package lsd.smartparking.model;

public class Policeman extends User {

	private String municipalityId;

	
    public Policeman() { }

	public Policeman(String id, String email, String name, String surname, String municipalityId) {
		super(id, email, name, surname);
		this.municipalityId = municipalityId;
	}

	public String getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(String municipalityId) {
		checkFields(municipalityId);
		this.municipalityId = municipalityId.trim();
	}
	
}