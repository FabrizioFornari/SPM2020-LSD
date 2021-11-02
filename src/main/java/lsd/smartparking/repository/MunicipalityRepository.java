package lsd.smartparking.repository;

import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lsd.smartparking.model.Municipality;

@ComponentScan
@Repository
public interface MunicipalityRepository extends MongoRepository<Municipality, String> {

    Optional<Municipality> findById(String id);

    Optional<Municipality> findByEmail(String email);
    
}