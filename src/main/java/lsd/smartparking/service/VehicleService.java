package lsd.smartparking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lsd.smartparking.model.Vehicle;
import lsd.smartparking.repository.VehicleRepository;

@ComponentScan
@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepo;


    public Optional<Vehicle> getVehicleById(String id) {
        return vehicleRepo.findById(id);
    }

    public List<Vehicle> getVehicleByOwner(String id) {
        return vehicleRepo.findByOwner(id);
    }

    public <T extends Vehicle> T addVehicle(T vehicle) {
        String plate = vehicle.getPlate();
        if (plate != null && !vehicleRepo.existsByPlateAndOwner(plate, vehicle.getOwner()).isEmpty()) return null;
        return vehicleRepo.save(vehicle);
    }

    @Transactional
    public String deleteVehicle(String id) {
        if (!vehicleRepo.existsById(id)) return null;
        vehicleRepo.deleteById(id);
        return id;
    }
    
}