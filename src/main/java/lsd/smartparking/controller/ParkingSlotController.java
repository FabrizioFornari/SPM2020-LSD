package lsd.smartparking.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.enums.VehicleType;
import lsd.smartparking.model.Coords;
import lsd.smartparking.model.ParkingSlot;
import lsd.smartparking.service.ParkingSlotService;

@RestController
@RequestMapping("/api/parkingslot")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService slotService;
    
    
    @GetMapping(value = "/", params = "id")
    public ResponseEntity<Optional<ParkingSlot>> getSlot(@RequestParam(required = true) String id) {
        Optional<ParkingSlot> slot = slotService.getSlot(id);
        return new ResponseEntity<>(slot, slot.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/", params = "parking")
    public ResponseEntity<List<ParkingSlot>> getSlots(@RequestParam(required = true) String parking, @RequestParam(required = false) VehicleType type) {
        List<ParkingSlot> slots = (type == null) ? slotService.getSlots(parking) : slotService.getSlots(parking, type);
    	return new ResponseEntity<>(slots, !slots.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/", params = {"topright", "botleft"})
    public ResponseEntity<List<ParkingSlot>> getSlots(@RequestParam(required = true) double[] topright, @RequestParam(required = true) double[] botleft) {
        List<ParkingSlot> slots = slotService.getSlots(new Coords(botleft), new Coords(topright));
    	return new ResponseEntity<>(slots, !slots.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyAuthority('MUNICIPALITY')")
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity<ParkingSlot> addSlot(Authentication auth, @Valid @RequestBody ParkingSlot slot) {
        if (!slotService.checkOwner(slot, auth.getName())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        slot = slotService.addSlot(slot);
    	return new ResponseEntity<>(slot, slot != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }
    
}