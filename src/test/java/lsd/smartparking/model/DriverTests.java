package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Driver.class})
public class DriverTests {

	private static String testEmail = "a@a.it";
	private static String testName = "Luca";
	private static String testSurname = "Verdi";
    private static Driver driver = new Driver(testEmail, testName, testSurname);
	
	
	@Test
	public void checkDriverFields() {
		assertEquals(driver.getClass(), Driver.class);
		assertEquals(driver.getName(), testName);
		assertEquals(driver.getSurname(), testSurname);
		assertEquals(driver.getEmail(), testEmail);
	}
	
}