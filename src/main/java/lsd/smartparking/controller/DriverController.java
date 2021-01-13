package lsd.smartparking.controller;

import java.util.UUID;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.model.Policeman;
import lsd.smartparking.model.Vehicle;

@RestController()
@RequestMapping("/api")
public class DriverController extends TokenChecker {

    private final String role = "driver";
	Firestore db = FirestoreClient.getFirestore();
    CollectionReference vehicleRef = db.collection("Vehicles");

    
    @PostMapping("add/vehicle/{driverId}/{token}")
    public @ResponseBody String addVehicle(@PathVariable("driverId") String driverId, @PathVariable("token") String token,
    		@RequestBody Vehicle vehicle) throws FirebaseAuthException {
        if (checkToken(driverId, token, role)) {
    		ApiFuture<DocumentReference> addedDocRef = vehicleRef.add(vehicle);
	        return (new Gson().toJson(addedDocRef));
        }
        return "Error";
    }

}