package lsd.smartparking.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

class TokenChecker {
   
    boolean checkToken(String uid, String token) throws FirebaseAuthException {
    	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
    	String decodedUid = decodedToken.getUid();
    	if (decodedUid.equals(uid)) return true;
		return false;
	}

	boolean checkToken(String uid, String token, String role) throws FirebaseAuthException {
    	FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
    	String decodedUid = decodedToken.getUid();
    	if (decodedUid.equals(uid) && Boolean.TRUE.equals(decodedToken.getClaims().get(role))) return true;
		return false;
	}

}