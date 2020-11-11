package lsd.smartparking.model;

public class Car {

	private String id;
	private String cod;
	private String owner;
	
	
	public Car() { }
	
	public Car(String id, String cod, String owner) {
		this.id = id;
		this.cod = cod;
		this.owner = owner;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCod() {
		return cod;
	}
	
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}