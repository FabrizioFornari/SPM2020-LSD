package lsd.smartparking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lsd.smartparking.model.Payment;

@ComponentScan
@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    Optional<Payment> findById(String id);

    List<Payment> findByPayer(String payer);

    List<Payment> findByTicket(String ticket);
    
}