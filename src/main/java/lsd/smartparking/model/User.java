package lsd.smartparking.model;

public class User {

	private String id;
	private String name;
	private String surname;
	private int age;
	private String email;

	
    public User() { }

	public User(String name, String surname, int age, String email, String id) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}