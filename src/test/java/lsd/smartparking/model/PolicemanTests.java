package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Policeman.class})
public class PolicemanTests {

	private static String testName = "Luca";
	private static String testSurname = "Verdi";
	private static String testEmail = "a@a.it";
	private static String testId = UUID.randomUUID().toString();
	private static String testMunicipalityId = UUID.randomUUID().toString();
	private static User user = new User(testId, testEmail, testName, testSurname);
    private static Policeman policeman = new Policeman(user.getId(), user.getEmail(), user.getName(), user.getSurname(), testMunicipalityId);
	private static Municipality municipality = new Municipality(testMunicipalityId, "b@b.it", "Roma", "Roma", "Lazio");
	
	
	@Test
	public void checkPolicemanFields() {
		assertEquals(policeman.getClass(), Policeman.class);
		assertEquals(policeman.getName(), testName);
		assertEquals(policeman.getSurname(), testSurname);
		assertEquals(policeman.getEmail(), testEmail);
		assertEquals(policeman.getId(), testId);
		assertEquals(policeman.getMunicipalityId(), testMunicipalityId);
	}
	
	@Test
	public void createPolicemanInvalidMunicipalityId() {
		assertThrows(IllegalArgumentException.class, () -> policeman.setMunicipalityId(null));
		assertThrows(IllegalArgumentException.class, () -> policeman.setMunicipalityId(""));
	}
	
	@Test
	public void checkPolicemanValidMunicipality() {
		assertEquals(testMunicipalityId, municipality.getId());
	}
	
}