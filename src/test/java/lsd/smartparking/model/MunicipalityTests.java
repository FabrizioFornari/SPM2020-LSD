package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Municipality.class})
public class MunicipalityTests {

	private static String testEmail = "a@a.it";
	private static String testCity = "Castelfidardo";
	private static String testProvince = "Ancona";
	private static String testRegion = "Marche";
    private static Municipality municipality = new Municipality(testEmail, testCity, testProvince, testRegion);

	
	@Test
	public void checkMunicipalityFields() {
		assertEquals(municipality.getClass(), Municipality.class);
		assertEquals(municipality.getCity(), testCity);
		assertEquals(municipality.getProvince(), testProvince);
		assertEquals(municipality.getRegion(), testRegion);
		assertEquals(municipality.getEmail(), testEmail);
	}
	
	@Test
	public void isMunicipalityApproved() {
		assertEquals(municipality.isApproved(), false);
	}
	
	@Test
	public void createMunicipalityInvalidCity() {
		assertThrows(IllegalArgumentException.class, () -> municipality.setCity(null));
		assertThrows(IllegalArgumentException.class, () -> municipality.setCity(" "));
	}
	
	@Test
	public void createMunicipalityInvalidProvince() {
		assertThrows(IllegalArgumentException.class, () -> municipality.setProvince(null));
		assertThrows(IllegalArgumentException.class, () -> municipality.setProvince(" "));
	}
	
	@Test
	public void createMunicipalityInvalidRegion() {
		assertThrows(IllegalArgumentException.class, () -> municipality.setRegion(null));
		assertThrows(IllegalArgumentException.class, () -> municipality.setRegion(" "));
	}
	
	@Test
	public void createMunicipalityInvalidEmail() {
		assertThrows(IllegalArgumentException.class, () -> municipality.setEmail(null));
		assertThrows(IllegalArgumentException.class, () -> municipality.setEmail(" "));
	}
	
}