package lsd.smartparking.controller;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.configuration.security.SecurityService;
import lsd.smartparking.enums.UserType;
import lsd.smartparking.model.Account;
import lsd.smartparking.model.Auth;
import lsd.smartparking.model.Driver;
import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Policeman;
import lsd.smartparking.service.AccountService;

@RestController()
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	AccountService accountService;
	@Autowired
	SecurityService securityService;


    @PreAuthorize("hasAnyAuthority('DRIVER, POLICEMAN, MUNICIPALITY')")
	@GetMapping("/")
	public ResponseEntity<?> getAccount() throws IOException, FirebaseAuthException {
		String id = securityService.getPrincipal();
		UserType type = securityService.getCredentials().getRole();
		Optional<? extends Account> account = null;
		if (type == UserType.MUNICIPALITY) account = accountService.getMunicipality(id);
		else account = accountService.getUser(id);
		return new ResponseEntity<>(account, account.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("isAnonymous()")
	@PostMapping(path = "driver", consumes = "application/json")
	public ResponseEntity<Driver> newDriver(@Valid @RequestBody Auth<Driver> user) {
		Driver driver = accountService.addDriver(user.getUser(), user.getPassword());
		return new ResponseEntity<Driver>(driver, driver != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
	}
	
	@PreAuthorize("isAnonymous()")
	@PostMapping(path = "/policeman", consumes = "application/json")
	public ResponseEntity<Policeman> newPoliceman(@Valid @RequestBody Auth<Policeman> user) {
		Policeman policeman = accountService.addPoliceman(user.getUser(), user.getPassword());
		return new ResponseEntity<Policeman>(policeman, policeman != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
	}
	
	@PreAuthorize("isAnonymous()")
	@PostMapping(path = "/municipality", consumes = "application/json")
	public ResponseEntity<Municipality> newMunicipality(@Valid @RequestBody Auth<Municipality> user) {
		Municipality municipality = user.getUser();
		municipality.setApproved(false);
		municipality.setDisabled(false);
		municipality = accountService.addMunicipality(municipality, user.getPassword());
		return new ResponseEntity<Municipality>(municipality, municipality != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
	}

}