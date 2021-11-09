package lsd.smartparking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import lsd.smartparking.enums.VehicleType;
import lsd.smartparking.model.ParkingSlot;

@Repository
public interface ParkingSlotRepository extends MongoRepository<ParkingSlot, String> {

    Optional<ParkingSlot> findById(String id);

    List<ParkingSlot> findByParking(String parking);

    @Query("{'parking' : ?0, 'type' : ?1}")
    List<ParkingSlot> findByParkingAndType(String parking, VehicleType type);

    List<ParkingSlot> findByCoordsWithin(Box box);

    @Query(value = "{'parking' : ?0}", delete = true)
    void deleteByParking(String id);
    
}