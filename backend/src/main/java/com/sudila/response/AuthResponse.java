package com.sudila.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate getters, setters, toString, etc.
@NoArgsConstructor // Lombok annotation for generating a no-argument constructor
@AllArgsConstructor // Lombok annotation for generating a constructor with all fields
public class AuthResponse {

    private String jwt; // JWT token to be returned in the authentication response
    private String message; // Optional message to accompany the authentication response

}
