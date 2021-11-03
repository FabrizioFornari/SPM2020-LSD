package lsd.smartparking.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.enums.VehicleType;
import lsd.smartparking.model.Coords;
import lsd.smartparking.model.Parking;
import lsd.smartparking.service.ParkingService;

@RestController
@RequestMapping(path = "/api/parking", consumes = "application/json")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;
    
    
    @GetMapping(value = "/", params = "id")
    public ResponseEntity<Optional<Parking>> getParking(@RequestParam(required = true) String id) {
        Optional<Parking> parking = parkingService.getParking(id);
        return new ResponseEntity<>(parking, parking.isPresent() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/", params = "owner")
    public ResponseEntity<List<Parking>> getParkings(@RequestParam(required = true) String owner) {
        List<Parking> parkings = parkingService.getParkings(owner);
    	return new ResponseEntity<>(parkings, parkings.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/", params = {"topright", "botleft"})
    public ResponseEntity<List<Parking>> getParkings(@RequestParam(required = true) double[] topright, @RequestParam(required = true) double[] botleft) {
        List<Parking> parkings = parkingService.getParkings(new Coords(botleft), new Coords(topright));
    	return new ResponseEntity<>(parkings, parkings.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/", params = {"topright", "botleft", "type"})
    public ResponseEntity<List<Parking>> getParkings(@RequestParam(required = true) double[] topright, @RequestParam(required = true) double[] botleft, @RequestParam(required = true) VehicleType type) {
        List<Parking> parkings = parkingService.getParkings(new Coords(botleft), new Coords(topright), type);
    	return new ResponseEntity<>(parkings, parkings.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyAuthority('MUNICIPALITY')")
    @PostMapping("/")
    public ResponseEntity<Parking> addParking(@Valid @RequestBody Parking parking) {
        Parking newParking = parkingService.addParking(parking);
    	return new ResponseEntity<>(newParking, newParking != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyAuthority('MUNICIPALITY')")
    @PostMapping("/edit")
    public ResponseEntity<Parking> editParking(@Valid @RequestBody Parking parking) {
        Parking editParking = parkingService.editParking(parking);
    	return new ResponseEntity<>(editParking, editParking != null ? HttpStatus.OK : HttpStatus.CONFLICT);
    }
    
}