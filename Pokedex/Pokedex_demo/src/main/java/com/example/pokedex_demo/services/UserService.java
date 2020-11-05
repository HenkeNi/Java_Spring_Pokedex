package com.example.pokedex_demo.services;

import com.example.pokedex_demo.entities.User;
import com.example.pokedex_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CREATES users every time program is run...
    // When users has been created; this method will run
    // PostConstruct is lika a constructor with all Spring related
    // injections finished, like @Autowired etc
    /*@PostConstruct
    public void initUsers() {
        //createUser( new User()); // TODO FIX CONSTRUCTOR
    }*/

    public List<User> findAll(String username) {
        if (username != null) {
            var user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find user by username %s.", username)));
            return List.of(user);
        }
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find user by id %s.", id))); // TODO: THROW EXCEPTION with status code
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(RuntimeException::new
        );
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }


    public User save(User user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Password not included!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void update(String id, User user) {

        // Check if user exist before updating
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find user by id %s.", id));
        }
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(String id) {

        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find user by id %s.", id));
        }
        userRepository.deleteById(id);
    }

}
