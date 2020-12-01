package lsd.smartparking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;

import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Parking;


@RestController()
@RequestMapping("/api")
public class MunicipalityController {
	
	Firestore db = FirestoreClient.getFirestore();
	CollectionReference parkingRef = db.collection("Parking");
	
	public boolean verifyMunicipality(String uid, String token) throws FirebaseAuthException {
    	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
    	String decodedUid = decodedToken.getUid();
    	if (decodedUid.equals(uid)) return true;
		return false;
	}
    
    @PostMapping("/municipality/{uid}/{token}/view/parking")
    public @ResponseBody String viewParking(@PathVariable("uid") String uid, @PathVariable("token") String token) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (verifyMunicipality(uid, token)) {
	    	ApiFuture<QuerySnapshot> future = parkingRef.get();
	        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	        List<Parking> allParking = new ArrayList<Parking>();
	    	for (QueryDocumentSnapshot document : documents) {
	    		Parking p = document.toObject(Parking.class);
	    		if (p.getMunicipalityId().equals(uid)) allParking.add(p);
	    	}
	        return (new Gson().toJson(allParking));
    	}
    	return "Errore";
    }
    
    @PostMapping("/municipality/{uid}/{token}/insert/parking/{name}/{lat}/{lon}")
    public @ResponseBody String insertParking(@PathVariable("uid") String uid, @PathVariable("token") String token,
    		@PathVariable("name") String name, @PathVariable("lat") double lat,
    		@PathVariable("lon") double lon) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (verifyMunicipality(uid, token)) {
    		Parking p = new Parking(UUID.randomUUID().toString(), name, lat, lon, uid);
    		ApiFuture<DocumentReference> addedDocRef = parkingRef.add(p);
	        return (new Gson().toJson(addedDocRef));
    	}
    	return "Errore";
    }
    
    @PostMapping("/municipality/{uid}/{token}/edit/parking")
    public @ResponseBody String editParking(@PathVariable("uid") String uid) throws InterruptedException, ExecutionException {
    	// TODO
		return "";
    }
    
    @PostMapping("/municipality/{uid}/{token}/delete/parking")
    public @ResponseBody String deleteParking(@PathVariable("uid") String uid) throws InterruptedException, ExecutionException {
    	// TODO
		return "";
    }

}