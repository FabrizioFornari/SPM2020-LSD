package lsd.smartparking.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.model.Bicycle;
import lsd.smartparking.model.Car;
import lsd.smartparking.model.Caravan;
import lsd.smartparking.model.Motorcycle;
import lsd.smartparking.model.Vehicle;
import lsd.smartparking.repository.VehicleRepository;
import lsd.smartparking.service.VehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepo;
    @Autowired
    VehicleService vehicleService;


    @GetMapping(value = "/", params = "id")
    public ResponseEntity<Optional<Vehicle>> getVehicle(@RequestParam(required = true) String id) {
        Optional<Vehicle> vehicle = vehicleService.findVehicleById(id);
    	return new ResponseEntity<>(vehicle, vehicle.isPresent() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/", params = "owner")
    public ResponseEntity<List<Vehicle>> getVehicles(@RequestParam(required = true) String owner) {
        List<Vehicle> vehicles = vehicleService.findVehicleByOwner(owner);
    	return new ResponseEntity<>(vehicles, !vehicles.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/car")
    public ResponseEntity<Vehicle> addCar(@Valid @RequestBody Car car) {
        car = vehicleService.addVehicle(car);
    	return new ResponseEntity<>(car, car != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PostMapping("/bicycle")
    public ResponseEntity<Vehicle> addBicycle(@Valid @RequestBody Bicycle bicycle) {
        bicycle = vehicleService.addVehicle(bicycle);
    	return new ResponseEntity<>(bicycle, bicycle != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PostMapping("/caravan")
    public ResponseEntity<Vehicle> addCaravan(@Valid @RequestBody Caravan caravan) {
        caravan = vehicleService.addVehicle(caravan);
    	return new ResponseEntity<>(caravan, caravan != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PostMapping("/motorcycle")
    public ResponseEntity<Vehicle> addMotorcycle(@Valid @RequestBody Motorcycle motorcycle) {
        motorcycle = vehicleService.addVehicle(motorcycle);
    	return new ResponseEntity<>(motorcycle, motorcycle != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @DeleteMapping(value = "/", params = "id")
    public ResponseEntity<Vehicle> deleteVehicle(@RequestParam(required = true) String id) {
        vehicleRepo.deleteById(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    /*


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Vehicle>> addCar(@PathVariable("id") String id) {
        Optional<Vehicle> vehicle = vehicleService.findVehicleById(id);
    	return new ResponseEntity<>(vehicle, vehicle.isPresent() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/car")
    public ResponseEntity<Vehicle> addCar(@Valid @RequestBody Car car) {
        Vehicle vehicle = vehicleService.addVehicle(car);
    	return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @PostMapping("/bicycle")
    public ResponseEntity<Vehicle> addBicycle(@Valid @RequestBody Bicycle bicycle) {
        Vehicle vehicle = vehicleService.addVehicle(bicycle);
    	return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") @NotBlank String id) {
        if (vehicleService.deleteVehicle(id) != null) return new ResponseEntity<>(id, HttpStatus.OK);
    	return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
    }
    */
    
}
