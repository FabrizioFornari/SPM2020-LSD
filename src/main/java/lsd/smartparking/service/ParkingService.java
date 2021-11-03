package lsd.smartparking.service;

import java.util.List;
import java.util.Optional;

import com.atlis.location.model.impl.Address;
import com.atlis.location.model.impl.MapPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.stereotype.Service;

import lsd.smartparking.controller.NominatimCustomAPI;
import lsd.smartparking.enums.VehicleType;
import lsd.smartparking.model.Coords;
import lsd.smartparking.model.Municipality;
import lsd.smartparking.model.Parking;
import lsd.smartparking.repository.MunicipalityRepository;
import lsd.smartparking.repository.ParkingRepository;

@Service
public class ParkingService {
    
    @Autowired
    private ParkingRepository parkingRepo;
    @Autowired
    private MunicipalityRepository municipalityRepo;
    

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

    public Parking addParking(Parking parking) {
        if (parkingRepo.existsById(parking.getId()) || !municipalityRepo.existsById(parking.getOwner())) return null;
        if (!checkAddress(parking)) return null;
        return parkingRepo.save(parking);
    }

    public Parking editParking(Parking parking) {
        if (!parkingRepo.existsById(parking.getId())) return null;
        if (!getParking(parking.getId()).get().getOwner().equals(parking.getOwner())) return null;
        return parkingRepo.save(parking);
    }

    private boolean checkAddress(Parking parking) {
        Coords coords = parking.getCoords();
        MapPoint mapPoint = new MapPoint().buildMapPoint(coords.getX(), coords.getY());
        Address address = NominatimCustomAPI.with("https://nominatim.openstreetmap.org/").getAddressFromMapPoint(mapPoint);
        Municipality municipality = municipalityRepo.findById(parking.getOwner()).get();
        if (municipality != null && (municipality.getCity().equals(address.getTown()) || parking.getCity().equals(address.getCity()))) return true;
        return false;
    }

}