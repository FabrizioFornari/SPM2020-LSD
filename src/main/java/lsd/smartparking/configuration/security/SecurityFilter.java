package lsd.smartparking.configuration.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private SecurityService securityService;
	@Autowired
	private FirebaseAuth firebaseAuth;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		authorize(request);
		filterChain.doFilter(request, response);
	}

	private void authorize(HttpServletRequest request) {
		String token = securityService.getBearerToken(request);
		if (token == null || token.isBlank()) return;
		try {
			DecodedToken decoded = new DecodedToken(firebaseAuth.verifyIdToken(token));
			String uid = decoded.getUid();
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(decoded.getRole().toString()));

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(uid, decoded, authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (BadCredentialsException e) {
			throw new SecurityException(e);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
	}

}