package lsd.smartparking.model;

import java.util.HashMap;

public class Car extends Vehicle {

	private String cod;
	private String plate;
	
	
	public Car() { }

	public Car(String id, String cod, String plate, String name, String owner) {
		this.setId(id);
		this.setCod(cod);
		this.setPlate(plate);
		this.setName(name);
		this.setOwner(owner);
	}
	
	public Car(String id, String cod, String plate, String name, String owner, HashMap<String, String> sharedOwners) {
		this.setId(id);
		this.setCod(cod);
		this.setPlate(plate);
		this.setName(name);
		this.setOwner(owner);
		this.setSharedOwners(sharedOwners);
	}
	
	public String getCod() {
		return this.cod;
	}
	
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public String getPlate() {
		return this.plate;
	}
	
	public void setPlate(String plate) {
		this.plate = plate;
	}
	
}