package lsd.smartparking.model;

import javax.validation.constraints.NotBlank;

import org.springframework.util.Assert;

public class Policeman extends User {
	
	@NotBlank(message = "MunicipalityId cannot be empty")
	private String municipalityId;


	public Policeman() { }

	public Policeman(String id, String email, String name, String surname, String municipalityId) {
		super(id, email, name, surname);
		Assert.hasText(municipalityId, "MunicipalityId cannot be empty");
		this.municipalityId = municipalityId;
	}

	public String getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(String municipalityId) {
		Assert.hasText(municipalityId, "MunicipalityId cannot be empty");
		this.municipalityId = municipalityId;
	}
	
}