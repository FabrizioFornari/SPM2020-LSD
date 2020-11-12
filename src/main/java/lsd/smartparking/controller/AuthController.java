package lsd.smartparking.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lsd.smartparking.model.User;


@Controller
public class AuthController {
	
	private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
	
	
	public static String tokenToUid(String token) {
        try {
			return FirebaseAuth.getInstance().verifyIdToken(token).getUid();
		} catch (FirebaseAuthException e) {
			System.out.println("Token parse error!");
			return null;
		}
	}

	@GetMapping("/auth")
    public String authLogin(ModelMap map) {
		return "auth/authMenu";
    }
	
	@GetMapping("/auth/register")
	public String register() {
    	return "auth/registerMenu";
	}
	
	@PostMapping("/auth/null") 
	public String logout() {
		return "auth/authMenu";
	}
	
	@GetMapping("/auth/password")
	public String password() {
		return "auth/passwordMenu";
	}

	@PostMapping("/login/user/{userId}/{userToken}")
	public DeferredResult<String> loginUser(@PathVariable("userId") String userId, @PathVariable("userToken") String userToken, Model model) throws FirebaseAuthException {
		DeferredResult<String> result = new DeferredResult<>();
		String uid = tokenToUid(userToken);

		if (uid.equals(userId) && FirebaseAuth.getInstance().getUser(uid).isEmailVerified()) {
			userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
	            @Override
	            public void onDataChange(DataSnapshot dataSnapshot) {
	            	User currentUser = dataSnapshot.getValue(User.class);
        			model.addAttribute("currentUser", currentUser);
					result.setResult("auth/welcomeUser");
	            }
	
	            @Override
	            public void onCancelled(DatabaseError databaseError) {
	            }
	        });
		} else
			result.setResult("error/403");

		return result;
	}
	
	@PostMapping("register/user/{uid}/{name}/{surname}/{age}/{email}")
	public DeferredResult<String> newUser(@PathVariable("uid") String uid, @PathVariable("name") String name, 
						   @PathVariable("surname") String surname,@PathVariable("age") int age,
						   @PathVariable("email") String email) throws IOException, FirebaseAuthException {
		DeferredResult<String> result = new DeferredResult<>();
		User u = new User(name, surname, age, email, uid);
		Map<String, Object> claims = new HashMap<>();
		claims.put("type", "user");
		FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
		 
		userRef.child(uid).setValue(u, new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
				if (databaseError != null) {
					System.out.println("Data could not be saved " + databaseError.getMessage());
				} else {
					result.setResult("auth/authMenu");
				}
			}
		});
        return result;
	}
}
