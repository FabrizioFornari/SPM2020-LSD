package lsd.smartparking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.stereotype.Service;

import lsd.smartparking.enums.VehicleType;
import lsd.smartparking.model.Coords;
import lsd.smartparking.model.Parking;
import lsd.smartparking.repository.ParkingRepository;

@Service
public class ParkingService {
    
    @Autowired
    private ParkingRepository parkingRepo;
    

    public Parking addParking(Parking parking) {
        return parkingRepo.save(parking);
    }

    public List<Parking> addParkings(List<Parking> parkings) {
        return parkingRepo.saveAll(parkings);
    }

    public Optional<Parking> getParking(String id) {
        return parkingRepo.findById(id);
    }

    public List<Parking> getParkings(String owner) {
        return parkingRepo.findByOwner(owner);
    }

    public List<Parking> getParkings(Coords topright, Coords botleft) {
        Box box = new Box(botleft.toPoint(), topright.toPoint());
        return parkingRepo.findByCoordsWithin(box);
    }

    public List<Parking> getParkings(Coords topright, Coords botleft, VehicleType type) {
        Box box = new Box(botleft.toPoint(), topright.toPoint());
        return parkingRepo.findByCoordsWithinAndType(box, type);
    }

}