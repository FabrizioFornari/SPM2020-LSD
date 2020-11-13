package lsd.smartparking.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;

import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Policeman;


@Controller
public class AuthController {
	
	Firestore db = FirestoreClient.getFirestore();
	DocumentReference policemanRef = db.collection("Policemen").document();
	DocumentReference municipalityRef = db.collection("Municipality").document();

	
	@PostMapping("register/policeman/{uid}/{name}/{surname}/{email}/{municipalityId}")
	public String newPoliceman(@PathVariable("uid") String uid, @PathVariable("name") String name, 
						   @PathVariable("surname") String surname, @PathVariable("email") String email,
						   @PathVariable("municipalityId") String municipalityId) throws IOException, FirebaseAuthException {
		Policeman p = new Policeman(name, surname, email, uid, municipalityId);
		policemanRef.set(p);
		return "";
	}
	
	@PostMapping("register/municipality/{uid}/{city}/{province}/{region}/{email}")
	public String newMunicipality(@PathVariable("uid") String uid, @PathVariable("city") String city, 
						   @PathVariable("province") String province, @PathVariable("region") String region,
						   @PathVariable("email") String email) throws IOException, FirebaseAuthException {
		Municipality m = new Municipality(city, province, region, uid, email);
		municipalityRef.set(m);
        return "";
	}
}
