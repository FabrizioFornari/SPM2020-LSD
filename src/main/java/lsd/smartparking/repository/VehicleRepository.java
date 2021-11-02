package lsd.smartparking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import lsd.smartparking.model.Vehicle;

@ComponentScan
@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    Optional<Vehicle> findById(String id);
    
    List<Vehicle> findByOwner(String owner);
    
    @Query("{'plate' : ?0}")
    List<Vehicle> findByPlate(String plate);

    @Query("{'plate' : ?0, 'owner' : ?1}")
    List<Vehicle> existsByPlateAndOwner(String plate, String owner);
    
}