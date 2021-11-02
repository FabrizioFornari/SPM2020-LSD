package lsd.smartparking.model;

import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import lsd.smartparking.enums.UserType;

@Document(collection = "users")
public class Driver extends User {
	
	private HashMap<String, String> vehicles;
	private HashMap<String, Payment> payments;
	private HashMap<String, Ticket> tickets;
	

	public Driver() { 
		this.setType(UserType.DRIVER);
	}
	
	@PersistenceConstructor
	public Driver(ObjectId id, String email, String name, String surname) {
		super(id, email, name, surname, UserType.DRIVER);
	}

	public Driver(String email, String name, String surname) {
		this(new ObjectId(), email, name, surname);
	}
	
	public HashMap<String, String> getVehicles() {
		return vehicles;
	}
	
	public String getVehicle(String id) {
		return vehicles.get(id);
	}
	
	public void setVehicles(HashMap<String, String> vehicles) {
		this.vehicles = vehicles;
	}

	public HashMap<String, Payment> getPayments() {
		return payments;
	}
	
	public Payment getPayment(String id) {
		return payments.get(id);
	}

	public void setPayments(HashMap<String, Payment> payments) {
		this.payments = payments;
	}

	public HashMap<String, Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(HashMap<String, Ticket> tickets) {
		this.tickets = tickets;
	}

}