package lsd.smartparking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Municipality.class})
public class MunicipalityTest {

    private Municipality municipality;
	private String testCity;
	private String testProvince;
	private String testRegion;
	private String testEmail;
	private String testId;
	
	@BeforeEach
	public void createMunicipality() {
		testCity = "Castelfidardo";
		testProvince = "Ancona";
		testRegion = "Marche";
		testEmail = "a@a.it";
		testId = UUID.randomUUID().toString();
		municipality = new Municipality(testCity, testProvince, testRegion, testEmail, testId);
	}
	
	@Test
	public void checkMunicipalityFields() {
		assertEquals(municipality.getClass(), Municipality.class);
		assertEquals(municipality.getCity(), testCity);
		assertEquals(municipality.getProvince(), testProvince);
		assertEquals(municipality.getRegion(), testRegion);
		assertEquals(municipality.getEmail(), testEmail);
		assertEquals(municipality.getId(), testId);
	}
	
	@Test
	public void isMunicipalityApproved() {
		assertEquals(municipality.isApproved(), false);
	}
	
	@Test
	public void createMunicipalityInvalidCity() {
		assertThrows(IllegalArgumentException.class, () -> municipality.setCity(null));
		assertThrows(IllegalArgumentException.class, () -> municipality.setCity(""));
		assertThrows(IllegalArgumentException.class, () -> municipality.setCity("123456789012345678901"));
	}
	
	@Test
	public void createMunicipalityInvalidProvince() {
		assertThrows(IllegalArgumentException.class, () -> municipality.setProvince(null));
		assertThrows(IllegalArgumentException.class, () -> municipality.setProvince(""));
		assertThrows(IllegalArgumentException.class, () -> municipality.setProvince("123456789012345678901"));
	}
	
	@Test
	public void createMunicipalityInvalidRegion() {
		assertThrows(IllegalArgumentException.class, () -> municipality.setRegion(null));
		assertThrows(IllegalArgumentException.class, () -> municipality.setRegion(""));
		assertThrows(IllegalArgumentException.class, () -> municipality.setRegion("123456789012345678901"));
	}
	
	@Test
	public void createMunicipalityInvalidEmail() {
		assertThrows(IllegalArgumentException.class, () -> municipality.setEmail(null));
		assertThrows(IllegalArgumentException.class, () -> municipality.setEmail(""));
		assertThrows(IllegalArgumentException.class, () -> municipality.setEmail("123456789012345678901"));
	}
	
	@Test
	public void createMunicipalityInvalidId() {
		assertThrows(IllegalArgumentException.class, () -> municipality.setId(null));
		assertThrows(IllegalArgumentException.class, () -> municipality.setId(""));
	}
	
	@Test
	public void addValidPolicemenToMunicipality() {
		Policeman policeman = new Policeman("Tizio", "Verdi", "tizio@verdi.it", UUID.randomUUID().toString(), municipality.getId());
		HashMap<String, Policeman> newPolicemen = municipality.getPolicemen();
		newPolicemen.put(policeman.getId(), policeman);
		assertEquals(newPolicemen, municipality.getPolicemen());
	}
	
	@Test
	public void addInvalidPolicemenToMunicipality() {
		HashMap<String, Policeman> newPolicemen = municipality.getPolicemen();
		Policeman policeman = new Policeman("Caio", "Lori", "caio@lori.it", UUID.randomUUID().toString(), "fasfafa");
		newPolicemen.put(policeman.getId(), policeman);
		assertThrows(IllegalArgumentException.class, () -> municipality.setPolicemen(newPolicemen));
	}
	
}