package lsd.smartparking.model;

public class User extends Utils {

	private String id;
	private String name;
	private String surname;
	private String email;


	public User(String id, String email, String name, String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
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
		checkFieldsLength(name, 30);
		this.name = name.trim();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		checkFields(surname);
		checkFieldsLength(surname, 30);
		this.surname = surname.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		checkFields(email);
		this.email = email.trim();
	}
	
}