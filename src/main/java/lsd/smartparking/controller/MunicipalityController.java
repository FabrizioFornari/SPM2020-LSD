package lsd.smartparking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atlis.location.model.impl.Address;
import com.atlis.location.model.impl.MapPoint;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;

import lsd.smartparking.model.Coords;
import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Parking;


@RestController()
@RequestMapping("/api")
public class MunicipalityController extends TokenChecker {
	
    private final String role = "municipality";
	Firestore db = FirestoreClient.getFirestore();
	CollectionReference parkingRef = db.collection("Parkings");
	CollectionReference municipalityRef = db.collection("Municipality");
    
	
    @PostMapping("/edit/municipality/{municipalityId}/{email}")
    public @ResponseBody String editEmail(@PathVariable("municipalityId") String municipalityId, @PathVariable("email") String email,
    		@PathVariable("token") String token) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(municipalityId, token, role)) {
    		ApiFuture<WriteResult> future = municipalityRef.document(municipalityId).update("email", email);
        	WriteResult result = future.get();
        	System.out.println("Write result: " + result);
    	}
    	return "Error";
    }
    
    @PostMapping("/{uid}/{token}/view/parking")
    public @ResponseBody String viewParking(@PathVariable("uid") String uid, @PathVariable("token") String token) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(uid, token, role)) {
	    	ApiFuture<QuerySnapshot> future = parkingRef.get();
	        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	        List<Parking> allParking = new ArrayList<Parking>();
	    	for (QueryDocumentSnapshot document : documents) {
	    		Parking p = document.toObject(Parking.class);
	    		if (p.getOwner().equals(uid)) allParking.add(p);
	    	}
	        return (new Gson().toJson(allParking));
    	}
    	return "Error";
    }
    
    @PostMapping("/add/parking/{municipalityId}/{token}")
    public @ResponseBody String addParking(@PathVariable("municipalityId") String municipalityId, @PathVariable("token") String token,
    		@RequestBody Parking parking) throws InterruptedException, ExecutionException, FirebaseAuthException {
        if (checkToken(municipalityId, token, role) && parking.getOwner().equals(municipalityId)) {
    		MapPoint mapPoint = new MapPoint();
			Address address = new Address();
			Coords coords = parking.getCoords();
			mapPoint = new MapPoint().buildMapPoint(coords.getX(), coords.getY());
			address = NominatimCustomAPI.with("https://nominatim.openstreetmap.org/").getAddressFromMapPoint(mapPoint);			
    		ApiFuture<DocumentSnapshot> future = municipalityRef.document(municipalityId).get();
    		DocumentSnapshot document = future.get();
    		Municipality m = null;
    		if (document.exists()) {
    			m = document.toObject(Municipality.class);
    			if (m.getCity().equals(address.getTown()) || m.getCity().equals(address.getCity())) {
                    Map<String, Object> parkingUpdates = new HashMap<>();
                    parkingUpdates.put("parking."+parking.getId()+".lat", coords.getX());
                    parkingUpdates.put("parking."+parking.getId()+".lon", coords.getY());
            		municipalityRef.document(municipalityId).update(parkingUpdates);
            		ApiFuture<WriteResult> writeResult = parkingRef.document(parking.getId()).set(parking);
			      	return (new Gson().toJson(writeResult));
    			} else {
        			return "Error";
    			}
    		} else {
    			return "Error";
    		}
    	}
    	return "Error";
    }
    
    @PostMapping("/edit/parking/{municipalityId}/{token}")
    public @ResponseBody String editParking(@PathVariable("municipalityId") String municipalityId, @PathVariable("token") String token,
    		@RequestBody Parking parking) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(municipalityId, token, role) && parking.getOwner().equals(municipalityId)) {
    		ApiFuture<WriteResult> editedParking = parkingRef.document(parking.getId()).set(parking);
	        return (new Gson().toJson(editedParking));
    	}
    	return "Error";
    }
    
    /* @PostMapping("/{municipalityId}/{token}/edit/parking/{uid}/{dayNumber}/{start}/{end}/{closed}")
    public @ResponseBody String editParkingDays(@PathVariable("municipalityId") String municipalityId, @PathVariable("token") String token,
    		@PathVariable("uid") String uid, @PathVariable("dayNumber") String dayNumber, @PathVariable("start") int start,
    		@PathVariable("end") int end, @PathVariable("closed") boolean closed) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(municipalityId, token, role)) {
    		Day day = new Day(start, end, closed);
    		ApiFuture<WriteResult> editedParkingDays = parkingRef.document(uid).collection("days").document(dayNumber).set(day);
	        return (new Gson().toJson(editedParkingDays));
    	}
    	return "Error";
    }
    
    @PostMapping("/{municipalityId}/{token}/edit/parking/{uid}/{dayNumber}/{roundNumber}/{start}/{end}/")
    public @ResponseBody String editParkingRounds(@PathVariable("municipalityId") String municipalityId, @PathVariable("token") String token,
    		@PathVariable("uid") String uid, @PathVariable("dayNumber") String dayNumber, @PathVariable("roundNumber") String roundNumber,
    		@PathVariable("start") int start, @PathVariable("end") int end) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(municipalityId, token, role) && parking.getMunicipalityId().equals(municipalityId)) {
    		Round round = new Round(start, end);
    		ApiFuture<WriteResult> editedParkingRounds = parkingRef.document(uid).collection("days").document(dayNumber).collection("rounds").document(roundNumber).set(round);
	        return (new Gson().toJson(editedParkingRounds));
    	}
    	return "Error";
    } 
    
    @PostMapping("/remove/parking/{municipalityId}/{token}")
    public @ResponseBody String removeParking(@PathVariable("municipalityId") String municipalityId, @PathVariable("token") String token,
    		@RequestBody Parking parking) throws InterruptedException, ExecutionException, FirebaseAuthException {
    	if (checkToken(municipalityId, token, role) && parking.getOwner().equals(municipalityId)) {
    		//Download all parkings of municipality
    		DocumentReference docRef = municipalityRef.document(municipalityId);
    		ApiFuture<DocumentSnapshot> future = docRef.get();
    		DocumentSnapshot document = future.get();
    		Municipality m = document.toObject(Municipality.class);
    		Map<String, HashMap<String, Double>> downloadParking = m.getParkings();
    		//Empty existing parking
    		Map<String, Object> emptyParking = new HashMap<>();
    		emptyParking.put("parking", FieldValue.delete());
    		municipalityRef.document(municipalityId).update(emptyParking);
    		//Create map of old parking, but remove the parking that has to been deleted
    		Map<String, Object> parkingUpdates = new HashMap<>();
            for (String keys : downloadParking.keySet()) {
	            for (HashMap<String, Double> values : downloadParking.values()) {
	                parkingUpdates.put("parking."+keys+".lat", values.get("lat"));
	                parkingUpdates.put("parking."+keys+".lon", values.get("lon"));
	            }
            }
            parkingUpdates.remove("parking."+parking.getId()+".lat");
            parkingUpdates.remove("parking."+parking.getId()+".lon");
            //Fill in the parking fields, the municipality parking, except old one
            if (parkingUpdates.isEmpty()) parkingUpdates.put("parking", FieldValue.delete());
    		municipalityRef.document(municipalityId).update(parkingUpdates);
    		//Delete parking from main collection
    		ApiFuture<WriteResult> writeResult = parkingRef.document(parking.getId()).delete();
	        return (new Gson().toJson(writeResult));
    	}
    	return "Error";
    }*/

}