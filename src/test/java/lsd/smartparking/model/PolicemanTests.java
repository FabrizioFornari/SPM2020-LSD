package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Policeman.class})
public class PolicemanTests {

    private Policeman policeman;
	private User user;
	private Municipality municipality;
	private String testMunicipalityId;
	private String testName;
	private String testSurname;
	private String testEmail;
	private String testId;
	
	@BeforeEach
	public void createPoliceman() {
		testName = "Luca";
		testSurname = "Verdi";
		testEmail = "a@a.it";
		testId = UUID.randomUUID().toString();
		testMunicipalityId = UUID.randomUUID().toString();
		municipality = new Municipality("Roma", "Roma", "Lazio", "b@b.it", testMunicipalityId);
		user = new User(testName, testSurname, testEmail, testId);
		policeman = new Policeman(user.getName(), user.getSurname(), user.getEmail(), user.getId(), testMunicipalityId);
	}
	
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