package lsd.smartparking.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lsd.smartparking.enums.TicketType;

@Document(collection = "tickets")
public class Ticket {
	
	@Id
	@NotNull(message = "Id cannot be null")
	private final ObjectId id;
	@NotBlank(message = "Payer cannot be empty")
	private String payer;
	@NotBlank(message = "Vehicle cannot be empty")
	private String vehicle;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@NotNull(message = "Inception cannot be null")
	private Date inception;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@NotNull(message = "Expiration cannot be null")
	private Date expiration;
	@NotBlank(message = "Parking cannot be empty")
	private String parking;
	@NotNull(message = "Type cannot be empty")
	private TicketType type;
	

	public Ticket() {
		this.id = new ObjectId();
	}

    @PersistenceConstructor
	public Ticket(ObjectId id, String payer, String vehicle, Date inception, Date expiration, String parking, TicketType type) {
		Assert.isTrue(id.getClass() == ObjectId.class, "Id must be valid");
		Assert.hasText(payer, "Payer cannot be empty");
		Assert.hasText(vehicle, "Vehicle cannot be empty");
		Assert.notNull(inception, "Expiration cannot be empty");
		Assert.notNull(expiration, "Expiration cannot be empty");
		Assert.isTrue(inception.before(expiration), "Inception must be before expiration");
		Assert.hasText(parking, "Parking cannot be empty");
		Assert.notNull(type, "Type cannot be empty");
		this.id = id;
		this.payer = payer;
		this.vehicle = vehicle;
		this.expiration = expiration;
		this.parking = parking;
	}

	public Ticket(String payer, String vehicle, Date inception, Date expiration, String parking, TicketType type) {
		this(new ObjectId(), payer, vehicle, inception, expiration, parking, type);
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
		return vehicle;
	}
	
	public void setVehicle(String vehicle) {
		Assert.hasText(vehicle, "Vehicle cannot be empty");
		this.vehicle = vehicle;
	}
	
	public Date getInception() {
		return inception;
	}
	
	public void setInception(Date inception) {
		Assert.notNull(inception, "Inception cannot be empty");
		if (this.expiration != null) Assert.isTrue(inception.before(this.expiration), "Inception must be before expiration");
		this.inception = inception;
	}
	
	public Date getExpiration() {
		return expiration;
	}
	
	public void setExpiration(Date expiration) {
		Assert.notNull(expiration, "Expiration cannot be empty");
		if (this.inception != null) Assert.isTrue(this.inception.before(expiration), "Expiration must be after inception");
		this.expiration = expiration;
	}
	
	public String getParking() {
		return parking;
	}
	
	public void setParking(String parking) {
		Assert.hasText(parking, "Parking cannot be empty");
		this.parking = parking;
	}

	public TicketType getType() {
		return this.type;
	}

	public void setType(TicketType type) {
		Assert.notNull(type, "Type cannot be empty");
		this.type = type;
	}	

}