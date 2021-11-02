package lsd.smartparking.repository;

import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import lsd.smartparking.model.User;

@ComponentScan
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);
    
}