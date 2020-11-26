package lsd.smartparking.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;

import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Policeman;


@RestController()
@RequestMapping("/api")
public class AuthController {
	
	Firestore db = FirestoreClient.getFirestore();
	CollectionReference policemanRef = db.collection("Policemen");
	CollectionReference municipalityRef = db.collection("Municipality");

	
	@PostMapping("register/policeman/{uid}/{name}/{surname}/{email}/{municipalityId}")
	public String newPoliceman(@PathVariable("uid") String uid, @PathVariable("name") String name, 
						   @PathVariable("surname") String surname, @PathVariable("email") String email,
						   @PathVariable("municipalityId") String municipalityId) throws IOException, FirebaseAuthException {
		Policeman p = new Policeman(name, surname, email, uid, municipalityId);
		policemanRef.document(uid).set(p);
		return "Successfully registered";
	}
	
	@PostMapping("register/municipality/{uid}/{city}/{province}/{region}/{email}")
	public String newMunicipality(@PathVariable("uid") String uid, @PathVariable("city") String city, 
						   @PathVariable("province") String province, @PathVariable("region") String region,
						   @PathVariable("email") String email) throws IOException, FirebaseAuthException {
		Municipality m = new Municipality(city, province, region, email, uid);
		municipalityRef.document(uid).set(m);
        return "Successfully registered";
	}

}