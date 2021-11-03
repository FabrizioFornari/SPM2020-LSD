package lsd.smartparking.configuration.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SecurityService {

	public String getPrincipal() {
		String userPrincipal = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Object principal = securityContext.getAuthentication().getPrincipal();
		if (principal instanceof String) {
			userPrincipal = ((String) principal);
		}
		return userPrincipal;
	}

	public DecodedToken getCredentials() {
		DecodedToken userCredentials = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Object credentials = securityContext.getAuthentication().getCredentials();
		if (credentials instanceof DecodedToken) {
			userCredentials = ((DecodedToken) credentials);
		}
		return userCredentials;
	}

	public String getBearerToken(HttpServletRequest request) {
		String bearerToken = null;
		String authorization = request.getHeader("Authorization");
		if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
			bearerToken = authorization.substring(7);
		}
		return bearerToken;
	}

}
