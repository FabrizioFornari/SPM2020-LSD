package lsd.smartparking;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class SmartParkingApplication {

	public static void main(String[] args) {
		try {
			HashMap<String, Object> admin = new HashMap<String, Object>();
			admin.put("uid", "crime-admin-server");
			
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("static/resources/file/serviceAccountKey.json").getInputStream()))
                .setDatabaseUrl("https://crimescraper.firebaseio.com")
                .setDatabaseAuthVariableOverride(admin)
                .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		SpringApplication.run(SmartParkingApplication.class, args);
	}

}
