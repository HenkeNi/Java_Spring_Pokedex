package com.example.pokedex_demo.repositories;

import com.example.pokedex_demo.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByName(String name);
    Optional<User> findByUsername(String username);
    User findByEmail(String email);

}
