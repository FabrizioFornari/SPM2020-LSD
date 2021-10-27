package lsd.smartparking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lsd.smartparking.model.Parking;

@ComponentScan
@Repository
public interface ParkingRepository extends MongoRepository<Parking, String> {

    Optional<Parking> findById(String id);

    List<Parking> findByOwner(String owner);

    List<Parking> findByCoordsWithin(Box box);
    
}