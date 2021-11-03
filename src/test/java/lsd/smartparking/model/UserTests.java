package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lsd.smartparking.enums.UserType;

@SpringBootTest(classes = {User.class})
public class UserTests {

	private static String testEmail = "a@a.it";
	private static String testName = "Mario";
	private static String testSurname = "Rossi";
	private static String testId = UUID.randomUUID().toString();
    private static User user = new User(testEmail, testName, testSurname, UserType.DRIVER);
	
	
	@Test
	public void checkUserFields() {
		assertEquals(user.getClass(), User.class);
		assertEquals(user.getName(), testName);
		assertEquals(user.getSurname(), testSurname);
		assertEquals(user.getEmail(), testEmail);
		assertEquals(user.getId(), testId);
	}
	
	@Test
	public void createUserInvalidName() {
		assertThrows(IllegalArgumentException.class, () -> user.setName(null));
		assertThrows(IllegalArgumentException.class, () -> user.setName(" "));
	}
	
	@Test
	public void createUserInvalidSurname() {
		assertThrows(IllegalArgumentException.class, () -> user.setSurname(null));
		assertThrows(IllegalArgumentException.class, () -> user.setSurname(" "));
	}
	
	@Test
	public void createUserInvalidEmail() {
		assertThrows(IllegalArgumentException.class, () -> user.setEmail(null));
		assertThrows(IllegalArgumentException.class, () -> user.setEmail(" "));
	}
	
}