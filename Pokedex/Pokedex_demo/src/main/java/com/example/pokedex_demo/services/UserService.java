package com.example.pokedex_demo.services;

import com.example.pokedex_demo.entities.User;
import com.example.pokedex_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> findAll() {
        return userRepo.findAll();
    }
    /*public List<User> findAll(String username) {
        if (username != null) {
            var user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find user by username %s.", username)));
            return List.of(user);
        }
        return userRepository.findAll();
    }*/

    public User findById(String id) {
        return userRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find user by id %s.", id))); // TODO: THROW EXCEPTION with status code
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(RuntimeException::new
        );
    }


    public User findByName(String name) {
        return userRepo.findByName(name).orElseThrow(RuntimeException::new);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User save(User user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Password not included!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }


    public void update(String id, User user) {
        var currentUser = this.getCurrentUser();
        if (!userRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(sameUserOrAdminOrEditor(currentUser, id)) {
            user.setId(id);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        }
        else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can not update this user");
    }


    private Boolean sameUserOrAdminOrEditor (User currentUser, String id) {
        return (currentUser.getId().equals(id)||currentUser.getRoles().contains("ADMIN")||currentUser.getRoles().contains("EDITOR"));
    }




    /*public void update(String id, User user) {

        // Check if user exist before updating
        if (!userRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find user by id %s.", id));
        }
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }*/

    public void delete(String id) {

        if (!userRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find user by id %s.", id));
        }
        userRepo.deleteById(id);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email);
    }




    /*@PostConstruct
    public void initUsers() {
        //String name, LocalDate birthdate, String username, String password, List<String> roles
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_EDITOR" , "ROLE_ADMIN");
        LocalDate date = LocalDate.now();
        String password = passwordEncoder.encode("secret");

        createUser( new User("Santa", date, "santa20", "santa@gmail.com" , password, roles)); // TODO FIX CONSTRUCTOR
    }*/

    public User createUser(User user) {
         System.out.println("Posting new user...");
        return userRepo.save(user);
    }

    public User registerNewUser(User user) {
        /*var existingUser = userRepository.findByUsername((user.getUsername()));
        if (existingUser !== null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already")
        }*/
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }









    /*public User findCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't fetch current user"));
    }*/

}
