package lsd.smartparking.controller;

import java.util.ArrayList;
import java.util.List;
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
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;

import lsd.smartparking.model.Municipality;

@RestController()
@RequestMapping("/api/admin")
public class AdminController {
	
	Firestore db = FirestoreClient.getFirestore();
	CollectionReference municipalityRef = db.collection("Municipality");
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    
	@GetMapping("/login")
    public @ResponseBody String loginAdmin() {
        LOG.info("GET successfully called on /secured resource");
        return "You are logged in";
    }
    
    @GetMapping("/view/municipalities")
    public @ResponseBody String viewMunicipalities() throws InterruptedException, ExecutionException {
    	ApiFuture<QuerySnapshot> future = municipalityRef.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Municipality> allMunicipalities = new ArrayList<Municipality>();
    	for (QueryDocumentSnapshot document : documents) {
    	    allMunicipalities.add(document.toObject(Municipality.class));
    	}
        return (new Gson().toJson(allMunicipalities));
    }
    
    @PostMapping("/approve/municipality/{uid}")
    public @ResponseBody String approveMunicipality(@PathVariable("uid") String uid) throws InterruptedException, ExecutionException {
    	ApiFuture<WriteResult> future = municipalityRef.document(uid).update("approved", true);
    	WriteResult result = future.get();
    	System.out.println("Write result: " + result);
        return "Municipality approved";
    }
    
    @PostMapping("/reject/municipality/{uid}")
    public @ResponseBody String rejectMunicipality(@PathVariable("uid") String uid) throws InterruptedException, ExecutionException {
    	ApiFuture<WriteResult> future = municipalityRef.document(uid).update("approved", false);
    	WriteResult result = future.get();
    	System.out.println("Write result: " + result);
        return "Municipality rejected";
    }
    
    @PostMapping("/enable/municipality/{uid}")
    public @ResponseBody String enableMunicipality(@PathVariable("uid") String uid) throws InterruptedException, ExecutionException {
    	ApiFuture<WriteResult> future = municipalityRef.document(uid).update("disabled", false);
    	WriteResult result = future.get();
    	System.out.println("Write result: " + result);
        return "Municipality enabled";
    }
    
    @PostMapping("/disable/municipality/{uid}")
    public @ResponseBody String disableMunicipality(@PathVariable("uid") String uid) throws InterruptedException, ExecutionException {
    	ApiFuture<WriteResult> future = municipalityRef.document(uid).update("disabled", true);
    	WriteResult result = future.get();
    	System.out.println("Write result: " + result);
        return "Municipality disabled";
    }

}