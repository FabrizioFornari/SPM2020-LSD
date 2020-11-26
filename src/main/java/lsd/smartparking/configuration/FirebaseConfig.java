package lsd.smartparking.configuration;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class FirebaseConfig {

    @PostConstruct
    public void initialize() throws IOException {
        HashMap<String, Object> admin = new HashMap<String, Object>();
        admin.put("uid", "crime-admin-server");
        
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("static/resources/file/serviceAccountKey.json").getInputStream()))
            .setDatabaseUrl("https://sparking-uni.firebaseio.com/")
            .setDatabaseAuthVariableOverride(admin)
            .build();
            
        if (FirebaseApp.getApps().isEmpty()) FirebaseApp.initializeApp(options);
    }

}