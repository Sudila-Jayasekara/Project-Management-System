package com.sudila.service;

import com.sudila.modal.User;
import com.sudila.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // Indicates that this class contains business logic
public class CustomeUserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Injects UserRepository for database operations

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user from the database based on the email (username)
        User user = userRepository.findByEmail(username);

        // Throw exception if user is not found
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Create an empty list of GrantedAuthority (roles)
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Return UserDetails object with user's email, password, and empty list of authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Username (email) used for authentication
                user.getPassword(), // Password retrieved from database
                authorities // Empty list of roles (for simplicity, roles can be added here)
        );
    }
}
