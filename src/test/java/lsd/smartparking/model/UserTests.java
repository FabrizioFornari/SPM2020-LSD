package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {User.class})
public class UserTests {

	private static String testName = "Mario";
	private static String testSurname = "Rossi";
	private static String testEmail = "a@a.it";
	private static String testId = UUID.randomUUID().toString();
    private static User user = new User(testId, testEmail, testName, testSurname);
	
	
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
		assertThrows(IllegalArgumentException.class, () -> user.setName(""));
		assertThrows(IllegalArgumentException.class, () -> user.setName("1234567890123456789012345678901"));
	}
	
	@Test
	public void createUserInvalidSurname() {
		assertThrows(IllegalArgumentException.class, () -> user.setSurname(null));
		assertThrows(IllegalArgumentException.class, () -> user.setSurname(""));
		assertThrows(IllegalArgumentException.class, () -> user.setSurname("1234567890123456789012345678901"));
	}
	
	@Test
	public void createUserInvalidEmail() {
		assertThrows(IllegalArgumentException.class, () -> user.setEmail(null));
		assertThrows(IllegalArgumentException.class, () -> user.setEmail(""));
	}
	
	@Test
	public void createUserInvalidId() {
		assertThrows(IllegalArgumentException.class, () -> new User(null, "email@email.com", "name", "surname"));
		assertThrows(IllegalArgumentException.class, () -> new User("", "email@email.com", "name", "surname"));
	}
	
}