package com.example.pokedex_demo.services;

import com.example.pokedex_demo.entities.User;
import com.example.pokedex_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public BCryptPasswordEncoder getEndcoder() { return encoder; }


    @Autowired
    private UserRepository userRepo;

    //@Autowired
    //private UserService userService;


    /*public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }*/

    @PostConstruct
    private void createDefaultUsers(){
        if (userRepo.findByEmail("user@gmail.com") == null) {
            addUser(new User("user@gmail.com", "password"));
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found by name: " + email);
        }
        return toUserDetails(user);
    }


    public User addUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            return userRepo.save(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    private UserDetails toUserDetails(User user) {
        // If you have a User entity you have to
        // use the userdetails User for this to work
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER").build();
    }


    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //System.out.printf("FLOADINg user by name", username);
        User user = userService.findByUsername(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User name " + username + " not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), getGrantedAuthorities(user));
    }*/

    /*private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }*/








}
