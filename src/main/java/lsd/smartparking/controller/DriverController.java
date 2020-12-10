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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.model.Car;

@RestController()
@RequestMapping("/api")
public class DriverController extends TokenChecker {

    private final String role = "driver";
	Firestore db = FirestoreClient.getFirestore();
    CollectionReference carRef = db.collection("Cars");

    
    @PostMapping("car/{uid}/{token}/{cod}/{plate}/{name}")
    public @ResponseBody String addCar(@PathVariable("uid") String uid, @PathVariable("token") String token,
            @PathVariable("cod") String cod, @PathVariable("plate") String plate, @PathVariable("name") String name) throws FirebaseAuthException {
        if (checkToken(uid, token, role)) {
            Car c = new Car(UUID.randomUUID().toString(), cod, plate, name, uid);
    		ApiFuture<DocumentReference> addedDocRef = carRef.add(c);
	        return (new Gson().toJson(addedDocRef));
        }
        return "Error";
    }

}