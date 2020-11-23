package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {User.class})
public class DriverTests {

    private Driver driver;
	private User user;
	private String testName;
	private String testSurname;
	private String testEmail;
	private String testId;
	private HashMap<String, Car> testCars;
	
	@BeforeEach
	public void createUser() {
		testName = "Luca";
		testSurname = "Verdi";
		testEmail = "a@a.it";
		testId = UUID.randomUUID().toString();
		user = new User(testName, testSurname, testEmail, testId);
		driver = new Driver(user.getName(), user.getSurname(), user.getEmail(), user.getId(), testCars);
	}
	
	@Test
	public void checkUserFields() {
		assertEquals(driver.getClass(), Driver.class);
		assertEquals(driver.getName(), testName);
		assertEquals(driver.getSurname(), testSurname);
		assertEquals(driver.getEmail(), testEmail);
		assertEquals(driver.getId(), testId);
		assertEquals(driver.getCars(), testCars);
	}
	
}