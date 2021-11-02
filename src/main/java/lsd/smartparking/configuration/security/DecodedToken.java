package lsd.smartparking.configuration.security;

import java.util.Map;

import com.google.firebase.auth.FirebaseToken;

public class DecodedToken {

    private FirebaseToken token;

    
    public DecodedToken(FirebaseToken token) {
		this.token = token;
	}

	public String getEmail() {
		return token.getEmail();
	}

	public String getIssuer() {
		return token.getIssuer();
	}

	public String getName() {
		return token.getName();
	}

	public String getUid() {
		return token.getUid();
	}

    public Map<String, Object> getClaims() {
        return token.getClaims();
    }

	public String getRole() {
		return (String) token.getClaims().get("role");
	}
    
}