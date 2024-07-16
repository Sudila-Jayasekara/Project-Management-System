package com.sudila.request;

import lombok.Data;

@Data // Lombok annotation to generate getters, setters, toString, etc.
public class LoginRequest {

    private String email; // Email address provided in the login request
    private String password; // Password provided in the login request

}
