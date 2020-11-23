package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {User.class})
public class PolicemanTests {

    private Policeman policeman;
	private User user;
	private String testName;
	private String testSurname;
	private String testEmail;
	private String testId;
	private String testMunicipalityId;
	
	@BeforeEach
	public void createUser() {
		testName = "Luca";
		testSurname = "Verdi";
		testEmail = "a@a.it";
		testId = UUID.randomUUID().toString();
		testMunicipalityId = UUID.randomUUID().toString();
		user = new User(testName, testSurname, testEmail, testId);
		policeman = new Policeman(user.getName(), user.getSurname(), user.getEmail(), user.getId(), testMunicipalityId);
	}
	
	@Test
	public void checkUserFields() {
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
	
}