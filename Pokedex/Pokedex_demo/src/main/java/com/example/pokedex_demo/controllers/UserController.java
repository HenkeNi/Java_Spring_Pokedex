package com.example.pokedex_demo.controllers;

import com.example.pokedex_demo.entities.User;
import com.example.pokedex_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // tell spring this is a rest controller
@RequestMapping("/rest/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping // ?username=
    @Secured("ROLE_USER") // tillåter alla användare att anropa
    public ResponseEntity<List<User>> findAllUsers(@RequestParam(required = false) String username) {
        var users = userService.findAll(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}") // res: /api/v1/users/xxxxxx
    @Secured({"ROLE_EDITOR", "ROLE_ADMIN"})
    public ResponseEntity<User> findUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /*@GetMapping("/username/{username}")
    public User findUserByUsername(@RequestParam String username) {
        return userService.findById(username);
    }*/


    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }


    @PutMapping("/{id}")
    @Secured({"ROLE_EDITOR", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String id, @RequestBody User user) {
        userService.update(id, user);
    }


    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }


}