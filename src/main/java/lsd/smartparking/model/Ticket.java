package lsd.smartparking.model;

public class Ticket {
	
	private String id;
	private String payee;
	private long emission;
	private long expiration;
	private String parking;
	
	
	public Ticket() { }

	public Ticket(String id, String payee, long emission, long expiration, String parking) {
		this.id = id;
		this.parking = parking;
		this.payee = payee;
		this.emission = emission;
		this.expiration = expiration;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPayee() {
		return payee;
	}
	
	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	public long getEmission() {
		return emission;
	}
	
	public void setEmission(long emission) {
		this.emission = emission;
	}
	
	public long getExpiration() {
		return expiration;
	}
	
	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}
	
	public String getParking() {
		return parking;
	}
	
	public void setParking(String parking) {
		this.parking = parking;
	}

}