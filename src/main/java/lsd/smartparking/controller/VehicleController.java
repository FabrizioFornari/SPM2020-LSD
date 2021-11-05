package lsd.smartparking.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.model.Bicycle;
import lsd.smartparking.model.Car;
import lsd.smartparking.model.Caravan;
import lsd.smartparking.model.Motorcycle;
import lsd.smartparking.model.Vehicle;
import lsd.smartparking.repository.VehicleRepository;
import lsd.smartparking.service.VehicleService;

@RestController
@PreAuthorize("hasAnyAuthority('DRIVER', 'POLICEMAN')")
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepo;
    @Autowired
    VehicleService vehicleService;


    @GetMapping("/")
    public ResponseEntity<List<Vehicle>> getVehicles(Authentication auth) {
        List<Vehicle> vehicles = vehicleService.getVehicleByOwner(auth.getName());
    	return new ResponseEntity<>(vehicles, !vehicles.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/car", consumes = "application/json")
    public ResponseEntity<Vehicle> addCar(@Valid @RequestBody Car car, Authentication auth) {
        if (!car.getOwner().equals(auth.getName())) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        car = vehicleService.addVehicle(car);
    	return new ResponseEntity<>(car, car != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PostMapping(path = "/bicycle", consumes = "application/json")
    public ResponseEntity<Vehicle> addBicycle(@Valid @RequestBody Bicycle bicycle, Authentication auth) {
        if (!bicycle.getOwner().equals(auth.getName())) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        bicycle = vehicleService.addVehicle(bicycle);
    	return new ResponseEntity<>(bicycle, bicycle != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PostMapping(path = "/caravan", consumes = "application/json")
    public ResponseEntity<Vehicle> addCaravan(@Valid @RequestBody Caravan caravan, Authentication auth) {
        if (!caravan.getOwner().equals(auth.getName())) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        caravan = vehicleService.addVehicle(caravan);
    	return new ResponseEntity<>(caravan, caravan != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PostMapping(path = "/motorcycle", consumes = "application/json")
    public ResponseEntity<Vehicle> addMotorcycle(@Valid @RequestBody Motorcycle motorcycle, Authentication auth) {
        if (!motorcycle.getOwner().equals(auth.getName())) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        motorcycle = vehicleService.addVehicle(motorcycle);
    	return new ResponseEntity<>(motorcycle, motorcycle != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathParam("id") String id, Authentication auth) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isEmpty() || !vehicle.get().getOwner().equals(auth.getName())) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        vehicleRepo.deleteById(id);
    	return new ResponseEntity<>(id, HttpStatus.OK);
    }
    
}