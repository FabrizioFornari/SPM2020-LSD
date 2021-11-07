package lsd.smartparking.model;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lsd.smartparking.enums.UserType;

@Document(collection = "users")
public class Policeman extends User {

	@NotBlank(message = "MunicipalityId cannot be empty")
	private String municipalityId;


	public Policeman() {
		this.setType(UserType.POLICEMAN);
	}

	@PersistenceConstructor
	public Policeman(ObjectId id, String email, String name, String surname, String municipalityId) {
		super(id, email, name, surname, UserType.POLICEMAN);
		Assert.hasText(municipalityId, "MunicipalityId cannot be empty");
		this.municipalityId = municipalityId;
	}

	public Policeman(String email, String name, String surname, String municipalityId) {
		this(new ObjectId(), email, name, surname, municipalityId);
	}

	public String getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(String municipalityId) {
		Assert.hasText(municipalityId, "MunicipalityId cannot be empty");
		this.municipalityId = municipalityId.trim();
	}
	
}