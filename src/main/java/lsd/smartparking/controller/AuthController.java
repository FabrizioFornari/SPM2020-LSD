package lsd.smartparking.controller;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.model.Auth;
import lsd.smartparking.model.Driver;
import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Policeman;
import lsd.smartparking.model.User;
import lsd.smartparking.service.AccountService;

@RestController()
@RequestMapping("/api/register")
public class AuthController {

	@Autowired
	AccountService accountService;

	
	@GetMapping(value = "/user", params = "id")
	public ResponseEntity<Optional<User>> getProva(@RequestParam(required = true) String id) throws IOException, FirebaseAuthException {
		Optional<User> user = accountService.getUser(id);
    	return new ResponseEntity<>(user, user.isPresent() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/driver")
	public ResponseEntity<Driver> newDriver(@Valid @RequestBody Auth<Driver> user) throws IOException, FirebaseAuthException {
		Driver driver = accountService.addDriver(user.getUser(), user.getPassword());
		return new ResponseEntity<Driver>(driver, driver != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
	}
	
	@PostMapping("/policeman")
	public ResponseEntity<Policeman> newPoliceman(@Valid @RequestBody Auth<Policeman> user) throws IOException, FirebaseAuthException {
		Policeman policeman = accountService.addPoliceman(user.getUser(), user.getPassword());
		return new ResponseEntity<Policeman>(policeman, policeman != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
	}
	
	@PostMapping("/municipality")
	public ResponseEntity<Municipality> newMunicipality(@Valid @RequestBody Auth<Municipality> user) throws IOException, FirebaseAuthException {
		Municipality municipality = user.getUser();
		municipality.setApproved(false);
		municipality.setDisabled(false);
		municipality = accountService.addMunicipality(municipality, user.getPassword());
		return new ResponseEntity<Municipality>(municipality, municipality != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
	}

}