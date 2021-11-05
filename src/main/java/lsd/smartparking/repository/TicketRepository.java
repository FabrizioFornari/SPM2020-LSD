package lsd.smartparking.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import lsd.smartparking.model.Ticket;

@ComponentScan
@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    Optional<Ticket> findById(String id);

    List<Ticket> findByPayer(String payer);

    List<Ticket> findByVehicle(String vehicle);

    List<Ticket> findByParking(String parking);

    @Query(value = "{'parking': ?2, $or: [{'inception':{ $gte: ?0, $lte: ?1 }}, {expiration:{ $gte: ?0, $lte: ?1 }}, {inception:{ $lte: ?0 }, expiration:{ $gte: ?1 }}]}", exists = true)
    Boolean existsByTimeRangeAndParking(Date inception, Date expiration, String parking);

    @Query(value = "{'parking': ?2, $or: [{'inception':{ $gte: ?0, $lte: ?1 }}, {expiration:{ $gte: ?0, $lte: ?1 }}, {inception:{ $lte: ?0 }, expiration:{ $gte: ?1 }}]}", exists = true)
    Boolean existsByTimeRangeAndVehicle(Date inception, Date expiration, String vehicle);
    
}