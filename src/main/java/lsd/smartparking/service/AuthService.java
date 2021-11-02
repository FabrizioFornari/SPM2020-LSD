package lsd.smartparking.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lsd.smartparking.enums.UserType;
import lsd.smartparking.model.Auth;
import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.User;
import lsd.smartparking.model.UserInfo;
import lsd.smartparking.repository.MunicipalityRepository;
import lsd.smartparking.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private FirebaseAuth firebaseAuth;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MunicipalityRepository municipalityRepo;


    public Optional<User> findProva(String id) {
        return userRepo.findById(id);
    }

    public <T extends UserInfo> T addUser(Auth<T> user) {
        UserRecord userRecord = null;
        try {
            userRecord = firebaseAuth.getUserByEmail(user.getEmail());
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        if (userRecord != null) return null;
        createUser(user);
        return user.getUser() instanceof Municipality ? 
            (T) municipalityRepo.save((Municipality) user.getUser()) :
            (T) userRepo.save((User) user.getUser());
    }

    private <T extends UserInfo> void createUser(Auth<T> prova) {
        CreateRequest request = new CreateRequest()
            .setUid(prova.getUser().getId())
            .setEmail(prova.getEmail())
            .setEmailVerified(false)
            .setPassword(prova.getPassword())
            .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = firebaseAuth.createUser(request);
            addRole(userRecord.getUid(), prova.getUser().getType());
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }

    }

	private void addRole(String uid, UserType role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role.toString());
		try {
            firebaseAuth.setCustomUserClaims(uid, claims);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
	}
    
}