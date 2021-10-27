package lsd.smartparking.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.model.Bycicle;
import lsd.smartparking.model.Car;
import lsd.smartparking.model.Vehicle;
import lsd.smartparking.repository.VehicleRepository;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepo;

    @PostMapping("/car")
    public ResponseEntity<Vehicle> addParking(@Valid @RequestBody Car car) {
        Car p = vehicleRepo.save(car);
        Vehicle v = vehicleRepo.findById(p.getId()).get();
    	return new ResponseEntity<>(v, HttpStatus.CREATED);
    }

    @PostMapping("/bycicle")
    public ResponseEntity<Vehicle> addParking(@Valid @RequestBody Bycicle bycicle) {
        Bycicle p = vehicleRepo.save(bycicle);
        Vehicle v = vehicleRepo.findById(p.getId()).get();
    	return new ResponseEntity<>(v, HttpStatus.CREATED);
    }
    
}
