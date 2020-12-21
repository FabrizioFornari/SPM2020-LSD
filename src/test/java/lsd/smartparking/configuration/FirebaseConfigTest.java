package lsd.smartparking.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Disabled
public class FirebaseConfigTest {

	@BeforeEach
	void testInitialize() throws IOException {
		HashMap<String, Object> admin = new HashMap<String, Object>();
        admin.put("uid", "crime-admin-server");
        
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("static/resources/file/serviceAccountKey.json").getInputStream()))
            .setDatabaseUrl("https://sparking-uni.firebaseio.com/")
            .setDatabaseAuthVariableOverride(admin)
            .build();
            
        FirebaseApp.initializeApp(options);
	}
	
	@Test
	void testFirestore() {
		DocumentReference docRef = FirestoreClient.getFirestore().collection("Users").document("D3VxZO00q3Pxir9BIRA8hZO5DVH3");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document;
		try {
			document = future.get();
			if (document.exists()) {
				System.out.println("Document data: " + document.getData());
				assertNotNull(document.getData());
			} else {
			  System.out.println("No such document!");
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			fail("Access not allowed");
		}
	}

}