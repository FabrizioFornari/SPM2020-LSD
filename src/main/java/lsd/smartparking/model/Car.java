package lsd.smartparking.model;

public class Car {

	private String id;
	private String cod;
	private String plate;
	private String name;
	private String owner;
	
	
	public Car() { }
	
	public Car(String id, String cod, String plate, String name, String owner) {
		this.id = id;
		this.cod = cod;
		this.plate = plate;
		this.name = name;
		this.owner = owner;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}