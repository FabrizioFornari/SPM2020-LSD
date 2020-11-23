package lsd.smartparking.model;

public class Policeman extends User {

	private String municipalityId;

	
    public Policeman() { }

	public Policeman(String name, String surname, String email, String id, String municipalityId) {
		super(name, surname, email, id);
		this.setMunicipalityId(municipalityId);
	}

	public String getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(String municipalityId) {
		checkFields(municipalityId);
		this.municipalityId = municipalityId.trim();
	}
	
}