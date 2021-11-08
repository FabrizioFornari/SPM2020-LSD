package lsd.smartparking.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.enums.VehicleType;
import lsd.smartparking.model.Coords;
import lsd.smartparking.model.Parking;
import lsd.smartparking.model.ParkingInfo;
import lsd.smartparking.service.ParkingService;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;
    
    
    @GetMapping(value = "/", params = "id")
    public ResponseEntity<Optional<Parking>> getParking(@RequestParam(required = true) String id) {
        Optional<Parking> parking = parkingService.getParking(id);
        return new ResponseEntity<>(parking, parking.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/", params = {"ne", "sw"})
    public ResponseEntity<List<ParkingInfo>> getParkings(@RequestParam(required = true) double[] ne, @RequestParam(required = true) double[] sw) {
        List<ParkingInfo> parkings = parkingService.getParkings(new Coords(sw), new Coords(ne));
    	return new ResponseEntity<>(parkings, HttpStatus.OK);
    }
    
    @GetMapping(value = "/", params = {"ne", "sw", "type"})
    public ResponseEntity<List<ParkingInfo>> getParkings(@RequestParam(required = true) double[] ne, @RequestParam(required = true) double[] sw, @RequestParam(required = true) VehicleType type) {
        List<ParkingInfo> parkings = parkingService.getParkings(new Coords(sw), new Coords(ne), type);
    	return new ResponseEntity<>(parkings, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyAuthority('MUNICIPALITY')")
    @GetMapping("/")
    public ResponseEntity<List<Parking>> getParkings(Authentication auth) {
        List<Parking> parkings = parkingService.getParkings(auth.getName());
    	return new ResponseEntity<>(parkings, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('MUNICIPALITY')")
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity<Parking> addParking(@Valid @RequestBody Parking parking, Authentication auth) {
        if (!parking.getOwner().equals(auth.getName())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Parking newParking = parkingService.addParking(parking);
    	return new ResponseEntity<>(newParking, newParking != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyAuthority('MUNICIPALITY')")
    @PostMapping(path = "/edit", consumes = "application/json")
    public ResponseEntity<Parking> editParking(@Valid @RequestBody Parking parking, Authentication auth) {
        Optional<Parking> p = parkingService.getParking(parking.getId());
        if (p.isEmpty() || !p.get().getOwner().equals(auth.getName()) || !p.get().getOwner().equals(parking.getOwner())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Parking editParking = parkingService.editParking(parking);
    	return new ResponseEntity<>(editParking, editParking != null ? HttpStatus.OK : HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyAuthority('MUNICIPALITY')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParking(@PathVariable("id") String id, Authentication auth) {
        Optional<Parking> p = parkingService.getParking(id);
        if (p.isEmpty() || !p.get().getOwner().equals(auth.getName())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        parkingService.deleteParking(id);
    	return new ResponseEntity<>(id, HttpStatus.OK);
    }
    
}