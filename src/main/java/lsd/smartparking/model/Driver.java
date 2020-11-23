package lsd.smartparking.model;

import java.util.HashMap;

public class Driver extends User {
	
	private HashMap<String, Car> cars;
	private HashMap<String, Payment> payments;
	
	
	public Driver() { }
	
	public Driver(String name, String surname, String email, String id, HashMap<String, Car> cars) {
		super(name, surname, email, id);
		this.setCars(cars);
	}
	
	public HashMap<String, Car> getCars() {
		return cars;
	}
	
	public Car getCar(String id) {
		return cars.get(id);
	}
	
	public void setCars(HashMap<String, Car> cars) {
		this.cars = cars;
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

}