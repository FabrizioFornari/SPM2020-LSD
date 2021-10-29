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

    @Query("{'inception' : ?0, 'expiration' : ?1}")
    List<Ticket> findByTimeRange(Date inception, Date expiration);
    
}