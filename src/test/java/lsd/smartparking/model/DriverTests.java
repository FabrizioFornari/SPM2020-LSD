package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Driver.class})
public class DriverTests {

	private static String testName = "Luca";
	private static String testSurname = "Verdi";
	private static String testEmail = "a@a.it";
	private static String testId = UUID.randomUUID().toString();
	private static HashMap<String, String> testCars;
	private static User user = new User(testId, testEmail, testName, testSurname);
    private static Driver driver = new Driver(user.getId(), user.getEmail(), user.getName(), user.getSurname(), testCars);
	
	
	@Test
	public void checkDriverFields() {
		assertEquals(driver.getClass(), Driver.class);
		assertEquals(driver.getName(), testName);
		assertEquals(driver.getSurname(), testSurname);
		assertEquals(driver.getEmail(), testEmail);
		assertEquals(driver.getId(), testId);
		assertEquals(driver.getVehicles(), testCars);
	}
	
}