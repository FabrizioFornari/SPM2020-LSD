package lsd.smartparking.model;

import java.util.HashMap;

public class Vehicle {
    
    private String id;
	private String name;
    private String type;
    private String cod;
	private String owner;
	private String plate;
	private HashMap<String, String> sharedOwners;
	

	public Vehicle() { }

	public Vehicle(String id, String name, String cod, String type, String owner, String plate) {
		this.id =  id;
		this.cod = cod;
		this.plate = plate;
		this.type = type;
		this.name = name;
		this.owner = owner;
	}
    
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
    }
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		if (!type.equals("car") &&
			!type.equals("motorcycle") &&
			!type.equals("bicycle") &&
			!type.equals("caravan") &&
			!type.equals("handicap"))
			throw new IllegalArgumentException("Invalid vehicle type");
		this.type = type;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public HashMap<String, String> getSharedOwners() {
		return this.sharedOwners;
	}
	
	public void setSharedOwners(HashMap<String, String> owners) {
		this.sharedOwners = owners;
	}
	
	public void addOwner(String id, String name) {
		this.sharedOwners.put(id, name);
	}
	
	public void removeOwner(String id) {
		this.sharedOwners.remove(id);
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

}