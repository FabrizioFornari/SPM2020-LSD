package lsd.smartparking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import lsd.smartparking.enums.VehicleType;
import lsd.smartparking.model.Parking;
import lsd.smartparking.model.ParkingInfo;

@ComponentScan
@Repository
public interface ParkingRepository extends MongoRepository<Parking, String> {

    Optional<Parking> findById(String id);

    List<Parking> findByOwner(String owner);

    @Query(value = "{ 'coords': { '$within': { '$box': ?0 }}}", fields = "{ 'coords': 1, 'price': 1 }")
    List<ParkingInfo> findByCoordsWithin(Box box);

    @Query(value = "{ 'coords': { '$within': { '$box': ?0 }}, 'slots.?1': { '$exists': true }}", fields = "{ 'coords': 1, 'price': 1 }")
    List<ParkingInfo> findByCoordsWithinAndType(Box box, VehicleType type);
    
}