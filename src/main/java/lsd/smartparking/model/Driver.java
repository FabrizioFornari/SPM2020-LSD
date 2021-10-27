package lsd.smartparking.model;

import java.util.HashMap;

public class Driver extends User {
	
	private HashMap<String, String> vehicles;
	private HashMap<String, Payment> payments;
	private HashMap<String, Ticket> tickets;
	

	public Driver() { }
	
	public Driver(String id, String email, String name, String surname, HashMap<String, String> vehicles) {
		super(id, email, name, surname);
		if (vehicles != null) this.vehicles = vehicles;
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