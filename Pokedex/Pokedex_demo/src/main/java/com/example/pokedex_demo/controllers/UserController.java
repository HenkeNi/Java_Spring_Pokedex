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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@ApiOperation(value = "/api/v1/users", tags = "User Controller")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource(name="authenticationManager")
    private AuthenticationManager authManager;

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
        //var users = userService.findAll(username);
        var users = userService.findAll();
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



    @GetMapping("/whoami")
    public ResponseEntity<User> whoami(HttpServletRequest req, HttpServletResponse res) {
        User user = userService.getCurrentUser();
        if(user==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }


    @PostMapping("/whoami")
    public ResponseEntity<User> whoami() {
        User user = userService.getCurrentUser();
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }



    @PostMapping("/login")
    public ResponseEntity<User> securityLogin(@RequestBody User user, HttpServletRequest req) {
        System.out.println("FROM POSTMAN");
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        Authentication auth = authManager.authenticate(authReq);

        if (!auth.isAuthenticated()) {
            throw new BadCredentialsException("Wrong username or password!");
        }
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return ResponseEntity.ok(userService.getCurrentUser());
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
