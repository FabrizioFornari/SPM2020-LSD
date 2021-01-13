package lsd.smartparking.model;

import java.util.HashMap;

public class Vehicle {
    
    private String id;
	private String name;
    private String type;
	private String owner;
	private HashMap<String, String> sharedOwners;
    

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

}