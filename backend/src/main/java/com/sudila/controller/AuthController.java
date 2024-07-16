package com.sudila.controller;

import com.sudila.config.JwtProvider;
import com.sudila.modal.User;
import com.sudila.repository.UserRepository;
import com.sudila.request.LoginRequest;
import com.sudila.response.AuthResponse;
import com.sudila.service.CustomeUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This class handles sign-in and sign-up requests
@RestController
@RequestMapping("/auth")
public class AuthController {

    // Automatically connects UserRepository to this controller
    @Autowired
    private UserRepository userRepository;

    // Automatically connects PasswordEncoder to this controller
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Automatically connects CustomeUserDetailsImpl to this controller
    @Autowired
    private CustomeUserDetailsImpl customeUserDetailsImpl;

    // This method handles user sign-up
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        // Check if the email is already used
        User isUserExist = userRepository.findByEmail(user.getEmail());
        if(isUserExist != null){
            throw new Exception("email already exist with another account");
        }

        // Create a new user and set their details
        User createdUser = new User();
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());

        // Save the new user in the database
        User savedUser = userRepository.save(createdUser);

        // Authenticate the new user
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token for the new user
        String jwt = JwtProvider.generateToken(authentication);

        // Create a response object with a success message and the JWT token
        AuthResponse res = new AuthResponse();
        res.setMessage("signup successful");
        res.setJwt(jwt);

        // Return the response with HTTP status CREATED (201)
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // This method handles user sign-in
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Authenticate the user using the provided username and password
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token for the user
        String jwt = JwtProvider.generateToken(authentication);

        // Create a response object with a success message and the JWT token
        AuthResponse res = new AuthResponse();
        res.setMessage("signin successful");
        res.setJwt(jwt);

        // Return the response with HTTP status CREATED (201)
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // This method checks if the username and password are correct
    private Authentication authenticate(String username, String password) {
        // Load the user details from the database
        UserDetails userDetails = customeUserDetailsImpl.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("username not found");
        }
        // Check if the password matches
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }
        // Return the authentication object with the user's details and authorities (permissions)
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
