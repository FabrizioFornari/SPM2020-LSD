package lsd.smartparking.model;

public class User extends Utils {

	private String id;
	private String name;
	private String surname;
	private String email;

	
    public User() { }

	public User(String name, String surname, String email, String id) {
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
		this.setId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		checkFields(id);
		this.id = id.trim();
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		checkFields(name);
		checkFieldsLength(name);
		this.name = name.trim();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		checkFields(surname);
		checkFieldsLength(surname);
		this.surname = surname.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		checkFields(email);
		checkFieldsLength(email);
		this.email = email.trim();
	}
	
}