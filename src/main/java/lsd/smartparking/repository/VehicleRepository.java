package lsd.smartparking.repository;

import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lsd.smartparking.model.Vehicle;

@ComponentScan
@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    Optional<Vehicle> findById(String id);
    
    Optional<Vehicle> findByOwner(String owner);
    
}