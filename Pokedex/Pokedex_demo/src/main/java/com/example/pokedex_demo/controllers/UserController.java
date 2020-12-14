package com.example.pokedex_demo.controllers;

import com.example.pokedex_demo.entities.User;
import com.example.pokedex_demo.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiOperation(value = "/api/v1/users", tags = "User Controller")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    //@Operation(summary = "Fetch all users")
    //@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Fetch all users", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<User>> findAllUsers(@RequestParam(required = false) String username) {
        var users = userService.findAll(username);
        return ResponseEntity.ok(users);
    }


    //@Secured({"ROLE_EDITOR", "ROLE_ADMIN"})
    @ApiOperation(value = "Fetch all users by Id", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/{id}") // res: /api/v1/users/xxxxxx
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<User> findUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }


    @ApiOperation(value = "Fetch all users by Username", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/username/{username}")
    @Secured("ROLE_ADMIN")
    public User findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }


    @ApiOperation(value = "Save user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> saveUser(@Validated @RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }


    //@Secured({"ROLE_EDITOR", "ROLE_ADMIN"})
    @ApiOperation(value = "Update user details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String id, @Validated @RequestBody User user) {
        userService.update(id, user);
    }


    @ApiOperation(value = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }


}
