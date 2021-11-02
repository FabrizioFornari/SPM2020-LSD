package lsd.smartparking.configuration;

import java.io.IOException;
import java.util.HashMap;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {

    @Primary
	@Bean
	public FirebaseApp getfirebaseApp() throws IOException {
		HashMap<String, Object> admin = new HashMap<String, Object>();
        admin.put("uid", "crime-admin-server");
        
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("static/resources/file/serviceAccountKey.json").getInputStream()))
            .setDatabaseUrl("https://sparking-uni.firebaseio.com/")
            .setDatabaseAuthVariableOverride(admin)
            .build();
            
        if (FirebaseApp.getApps().isEmpty()) FirebaseApp.initializeApp(options);
        
		return FirebaseApp.getInstance();
	}

	@Bean
	public FirebaseAuth getAuth() throws IOException {
		return FirebaseAuth.getInstance(getfirebaseApp());
	}

}