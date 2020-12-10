package lsd.smartparking.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

class TokenChecker {

	boolean checkToken(String uid, String token, String role) throws FirebaseAuthException {
    	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
    	String decodedUid = decodedToken.getUid();
    	if (decodedUid.equals(uid) && Boolean.TRUE.equals(decodedToken.getClaims().get(role))) return true;
		return false;
	}

	void claimToken(String uid, String role) throws FirebaseAuthException {
		Map<String, Object> claims = new HashMap<>();
		claims.put(role, true);
		FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
	}

}