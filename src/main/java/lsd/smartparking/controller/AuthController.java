package lsd.smartparking.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Policeman;
import lsd.smartparking.model.User;


@Controller
public class AuthController {
	
	private DatabaseReference policemanRef = FirebaseDatabase.getInstance().getReference("Policemen");
	private DatabaseReference municipalityRef = FirebaseDatabase.getInstance().getReference("Municipality");
	
	@PostMapping("register/user/{uid}/{name}/{surname}/{email}/{municipalityId}")
	public DeferredResult<String> newPoliceman(@PathVariable("uid") String uid, @PathVariable("name") String name, 
						   @PathVariable("surname") String surname, @PathVariable("email") String email,
						   @PathVariable("municipalityId") String municipalityId) throws IOException, FirebaseAuthException {
		DeferredResult<String> result = new DeferredResult<>();
		Policeman p = new Policeman(name, surname, email, uid, municipalityId);
		 
		policemanRef.child(uid).setValue(p, new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
				if (databaseError != null) {
					System.out.println("Data could not be saved " + databaseError.getMessage());
				} else {
					//TODO set Result for frontend
				}
			}
		});
        return result;
	}
	
	@PostMapping("register/user/{uid}/{city}/{province}/{region}/{email}")
	public DeferredResult<String> newMunicipality(@PathVariable("uid") String uid, @PathVariable("city") String city, 
						   @PathVariable("province") String province, @PathVariable("region") String region,
						   @PathVariable("email") String email) throws IOException, FirebaseAuthException {
		DeferredResult<String> result = new DeferredResult<>();
		Municipality m = new Municipality(city, province, region, uid, email);
		 
		municipalityRef.child(uid).setValue(m, new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
				if (databaseError != null) {
					System.out.println("Data could not be saved " + databaseError.getMessage());
				} else {
					//TODO set Result for frontend
				}
			}
		});
        return result;
	}
}
