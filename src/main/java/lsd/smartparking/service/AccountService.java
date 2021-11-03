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
import lsd.smartparking.model.Driver;
import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Policeman;
import lsd.smartparking.model.User;
import lsd.smartparking.model.Account;
import lsd.smartparking.repository.MunicipalityRepository;
import lsd.smartparking.repository.UserRepository;

@Service
public class AccountService {

	@Autowired
	private FirebaseAuth firebaseAuth;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MunicipalityRepository municipalityRepo;


    public Optional<User> getUser(String id) {
        return userRepo.findById(id);
    }

    public Optional<Municipality> getMunicipality(String id) {
        return municipalityRepo.findById(id);
    }

    public Driver addDriver(Driver user, String password) {        
        if (!createAccount(user, password)) return null;
        return userRepo.save(user);
    }

    public Policeman addPoliceman(Policeman policeman, String password) {
        if (municipalityRepo.findById(policeman.getMunicipalityId()).isEmpty()) return null;
        if (!createAccount(policeman, password)) return null;
        return userRepo.save(policeman);
    }

    public Municipality addMunicipality(Municipality municipality, String password) {
        if (!createAccount(municipality, password)) return null;
        return municipalityRepo.save(municipality);
    }

    public boolean createAccount(Account account, String password) {
        try {
            if (firebaseAuth.getUserByEmail(account.getEmail()) != null) return false;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        UserRecord userRecord;
        CreateRequest request = new CreateRequest()
            .setUid(account.getId())
            .setEmail(account.getEmail())
            .setPassword(password)
            .setEmailVerified(false)
            .setDisabled(false);
        try {
            userRecord = firebaseAuth.createUser(request);
            addRole(userRecord.getUid(), account.getType());
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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