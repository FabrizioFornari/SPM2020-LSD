package lsd.smartparking.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;

import lsd.smartparking.model.Driver;
import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Policeman;


@RestController()
@RequestMapping("/api/register")
public class AuthController extends TokenChecker {
	
	Firestore db = FirestoreClient.getFirestore();
	CollectionReference driverRef = db.collection("Users");
	CollectionReference policemanRef = db.collection("Policemen");
	CollectionReference municipalityRef = db.collection("Municipality");

	
	@PostMapping("/driver")
	public String newDriver(@Valid @RequestBody Driver driver) throws IOException, FirebaseAuthException {
		driverRef.document(driver.getId()).set(driver);
		claimToken(driver.getId(), "driver");
		return "Successfully registered";
	}

	@PostMapping("/policeman")
	public String newPoliceman(@Valid @RequestBody Policeman policeman) throws IOException, FirebaseAuthException {
		policemanRef.document(policeman.getId()).set(policeman);
		claimToken(policeman.getId(), "policeman");
		return "Successfully registered";
	}
	
	@PostMapping("/municipality")
	public String newMunicipality(@Valid @RequestBody Municipality municipality) throws IOException, FirebaseAuthException {
		municipalityRef.document(municipality.getId()).set(municipality);
		claimToken(municipality.getId(), "municipality");
        return "Successfully registered";
	}

}