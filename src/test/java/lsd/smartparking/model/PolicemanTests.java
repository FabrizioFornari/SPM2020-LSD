package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Policeman.class})
public class PolicemanTests {

	private static String testEmail = "a@a.it";
	private static String testName = "Luca";
	private static String testSurname = "Verdi";
	private static Municipality municipality = new Municipality("b@b.it", "Roma", "Roma", "Lazio");
    private static Policeman policeman = new Policeman(testEmail, testName, testSurname, municipality.getId());
	
	
	@Test
	public void checkPolicemanFields() {
		assertEquals(policeman.getClass(), Policeman.class);
		assertEquals(policeman.getEmail(), testEmail);
		assertEquals(policeman.getName(), testName);
		assertEquals(policeman.getSurname(), testSurname);
		assertEquals(policeman.getMunicipalityId(), municipality.getId());
	}
	
	@Test
	public void createPolicemanInvalidMunicipalityId() {
		assertThrows(IllegalArgumentException.class, () -> policeman.setMunicipalityId(null));
		assertThrows(IllegalArgumentException.class, () -> policeman.setMunicipalityId(" "));
	}
	
}