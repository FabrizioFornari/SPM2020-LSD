package lsd.smartparking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.stereotype.Service;

import lsd.smartparking.enums.VehicleType;
import lsd.smartparking.model.Coords;
import lsd.smartparking.model.Parking;
import lsd.smartparking.model.ParkingSlot;
import lsd.smartparking.repository.ParkingRepository;
import lsd.smartparking.repository.ParkingSlotRepository;

@Service
public class ParkingSlotService {
    
    @Autowired
    private ParkingRepository parkingRepo;
    @Autowired
    private ParkingSlotRepository slotRepo;
    

    public ParkingSlot addSlot(ParkingSlot slot) {
        if (!parkingRepo.existsById(slot.getParking())) return null;
        return slotRepo.save(slot);
    }

    public List<ParkingSlot> addSlots(List<ParkingSlot> slots) {
        for (ParkingSlot slot : slots)
            if (!parkingRepo.existsById(slot.getParking())) return null;
        
        return slotRepo.saveAll(slots);
    }

    public Optional<ParkingSlot> getSlot(String id) {
        return slotRepo.findById(id);
    }

    public List<ParkingSlot> getSlots(String parking) {
        return slotRepo.findByParking(parking);
    }

    public List<ParkingSlot> getSlots(String parking, VehicleType type) {
        return slotRepo.findByParkingAndType(parking, type);
    }

    public List<ParkingSlot> getSlots(Coords topright, Coords botleft) {
        Box box = new Box(botleft.toPoint(), topright.toPoint());
        return slotRepo.findByCoordsWithin(box);
    }

    public String deleteSlot(String id) {
        if (!slotRepo.existsById(id)) return null;
        slotRepo.deleteById(id);
        return id;
    }

}