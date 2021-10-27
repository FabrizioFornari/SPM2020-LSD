package lsd.smartparking.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

@Document(collection = "payments")
public class Payment {

	@Id
	@NotNull(message = "Id cannot be null")
	private final ObjectId id;
	@NotBlank(message = "Payer cannot be empty")
	private final String payer;
	@NotNull(message = "Amount cannot be null")
	private final double amount;
	@NotNull(message = "Emission cannot be null")
	private final Date emission;
	@NotBlank(message = "Ticket cannot be empty")
	private final String ticket;
	

	public Payment(ObjectId id, String payer, double amount, @Valid Date emission, String ticket) {
		Assert.isTrue(id.getClass() == ObjectId.class, "Id must be valid");
		Assert.hasText(payer, "Payer cannot be empty");
		Assert.notNull(amount, "Amount cannot be null");
		Assert.hasText(ticket, "Ticket cannot be empty");
		this.id = id;
		this.payer = payer;
		this.amount = amount;
		this.emission = emission;
		this.ticket = ticket;
	}
	
	public String getId() {
		return id.toHexString();
	}
	
	public String getPayer() {
		return payer;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public Date getEmission() {
		return emission;
	}
	
	public String getTicket() {
		return ticket;
	}
	
}