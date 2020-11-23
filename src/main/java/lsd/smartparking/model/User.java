package lsd.smartparking.model;

public class User {

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
		if (name.length() > 20) {
			throw new IllegalArgumentException("The name is too long");
		}
		this.name = name.trim();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		checkFields(surname);
		if (surname.length() > 20) {
			throw new IllegalArgumentException("The surname is too long");
		}
		this.surname = surname.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		checkFields(email);
		if (email.length() > 20) {
			throw new IllegalArgumentException("The email is too long");
		}
		this.email = email.trim();
	}
	
	private void checkFields(String field) {
		if (field == null || field == "") {
			throw new IllegalArgumentException("The field cannot be null or empty");
		}
	}
	
}