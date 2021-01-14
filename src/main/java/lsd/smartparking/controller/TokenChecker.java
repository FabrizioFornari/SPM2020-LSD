package lsd.smartparking.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

abstract class TokenChecker {

	boolean checkToken(String uid, String token, String role) throws FirebaseAuthException {
    	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
		String decodedUid = decodedToken.getUid();
    	if (decodedUid.equals(uid) && decodedToken.getClaims().get("role").equals(role)) return true;
		return false;
	}

	void claimToken(String uid, String role) throws FirebaseAuthException {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
	}

}