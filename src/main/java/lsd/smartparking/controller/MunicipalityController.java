package lsd.smartparking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;

import lsd.smartparking.model.Parking;


@RestController()
@RequestMapping("/api/municipality")
public class MunicipalityController extends TokenChecker {
	
    private final String role = "municipality";
	Firestore db = FirestoreClient.getFirestore();
	CollectionReference parkingRef = db.collection("Parkings");

    
    @PostMapping("/{uid}/{token}/view/parking")
    public @ResponseBody String viewParking(@PathVariable("uid") String uid, @PathVariable("token") String token) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(uid, token, role)) {
	    	ApiFuture<QuerySnapshot> future = parkingRef.get();
	        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	        List<Parking> allParking = new ArrayList<Parking>();
	    	for (QueryDocumentSnapshot document : documents) {
	    		Parking p = document.toObject(Parking.class);
	    		if (p.getMunicipalityId().equals(uid)) allParking.add(p);
	    	}
	        return (new Gson().toJson(allParking));
    	}
    	return "Error";
    }
    
    @PostMapping("/{municipalityId}/{token}/insert/parking/{name}/{lat}/{lon}")
    public @ResponseBody String addParking(@PathVariable("municipalityId") String municipalityId, @PathVariable("token") String token,
    		@PathVariable("name") String name, @PathVariable("lat") double lat,
    		@PathVariable("lon") double lon) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(municipalityId, token, role)) {
    		Parking p = new Parking(UUID.randomUUID().toString(), name, lat, lon, municipalityId);
    		ApiFuture<DocumentReference> addedDocRef = parkingRef.add(p);
	        return (new Gson().toJson(addedDocRef));
    	}
    	return "Error";
    }
    
    @PostMapping("/{municipalityId}/{token}/edit/parking/{uid}/{name}/{lat}/{lon}")
    public @ResponseBody String editParking(@PathVariable("municipalityId") String municipalityId, @PathVariable("token") String token,
    		@PathVariable("uid") String uid, @PathVariable("name") String name, @PathVariable("lat") double lat,
    		@PathVariable("lon") double lon) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(municipalityId, token, role)) {
    		Map<String, Object> parkingFields = new HashMap<>();
    		parkingFields.put("name", name);
    		parkingFields.put("lat", lat);
    		parkingFields.put("lon", lon);
    		ApiFuture<WriteResult> editedParking = parkingRef.document(uid).update(parkingFields);
	        return (new Gson().toJson(editedParking));
    	}
    	return "Error";
    }
    
    @PostMapping("/{municipalityId}/{token}/delete/parking/{id}/")
    public @ResponseBody String deleteParking(@PathVariable("municipalityId") String municipalityId, @PathVariable("token") String token,
    		@PathVariable("id") String id) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(municipalityId, token, role)) {
    		ApiFuture<WriteResult> writeResult = parkingRef.document(id).delete();
	        return (new Gson().toJson(writeResult));
    	}
    	return "Error";
    }

}