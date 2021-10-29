package lsd.smartparking.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

@Document(collection = "tickets")
public class Ticket {
	
	@Id
	@NotNull(message = "Id cannot be null")
	private final ObjectId id;
	@NotBlank(message = "Payer cannot be empty")
	private String payer;
	@NotBlank(message = "Vehicle cannot be empty")
	private String vehicle;
	@NotNull(message = "Emission cannot be null")
	private Date emission;
	@NotNull(message = "Inception cannot be null")
	private Date inception;
	@NotNull(message = "Expiration cannot be null")
	private Date expiration;
	@NotBlank(message = "Parking cannot be empty")
	private String parking;
	

	public Ticket(ObjectId id, String payer, String vehicle, Date emission, Date expiration, String parking) {
		Assert.isTrue(id.getClass() == ObjectId.class, "Id must be valid");
		Assert.hasText(payer, "Payer cannot be empty");
		Assert.hasText(vehicle, "Vehicle cannot be null");
		Assert.notNull(emission, "Emission cannot be empty");
		Assert.notNull(expiration, "Expiration cannot be empty");
		this.id = id;
		this.payer = payer;
		this.vehicle = vehicle;
		this.emission = emission;
		this.expiration = expiration;
		this.parking = parking;
	}
	
	public String getId() {
		return id.toHexString();
	}
	
	public String getPayer() {
		return payer;
	}
	
	public void setPayer(String payer) {
		Assert.hasText(payer, "Payer cannot be empty");
		this.payer = payer;
	}
	
	public String getVehicle() {
		return payer;
	}
	
	public void setVehicle(String vehicle) {
		Assert.hasText(vehicle, "Vehicle cannot be empty");
		this.vehicle = vehicle;
	}
	
	public Date getEmission() {
		return emission;
	}
	
	public void setEmission(Date emission) {
		Assert.notNull(emission, "Emission cannot be empty");
		this.emission = emission;
	}
	
	public Date getInception() {
		return inception;
	}
	
	public void setInception(Date inception) {
		Assert.notNull(inception, "Emission cannot be empty");
		this.inception = inception;
	}
	
	public Date getExpiration() {
		return expiration;
	}
	
	public void setExpiration(Date expiration) {
		Assert.notNull(emission, "Expiration cannot be empty");
		this.expiration = expiration;
	}
	
	public String getParking() {
		return parking;
	}
	
	public void setParking(String parking) {
		Assert.hasText(parking, "Parking cannot be empty");
		this.parking = parking;
	}

}