package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {User.class})
public class UserTests {

    private User user;
	private String testName;
	private String testSurname;
	private String testEmail;
	private String testId;
	
	@BeforeEach
	public void createUser() {
		testName = "Mario";
		testSurname = "Rossi";
		testEmail = "a@a.it";
		testId = UUID.randomUUID().toString();
		user = new User(testName, testSurname, testEmail, testId);
	}
	
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
		assertThrows(IllegalArgumentException.class, () -> user.setEmail("1234567890123456789012345678901"));
	}
	
	@Test
	public void createUserInvalidId() {
		assertThrows(IllegalArgumentException.class, () -> user.setId(null));
		assertThrows(IllegalArgumentException.class, () -> user.setId(""));
	}
	
}