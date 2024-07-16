package com.sudila.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {
    // Static key used for JWT signing and validation
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Generates a JWT token after successful authentication
    public static String generateToken(Authentication auth) {
        // Building the JWT token with issued date, expiration, and email claim
        return Jwts.builder()
                .setIssuedAt(new Date()) // Sets the token issue date
                .setExpiration(new Date(new Date().getTime() + 86400000)) // Sets token expiration to 24 hours from now
                .claim("email", auth.getName()) // Sets email claim based on authenticated user's name
                .signWith(key) // Signs the token with the secret key
                .compact(); // Compacts the token into its final form
    }

    // Extracts email from the given JWT token
    public static String getEmailFromToken(String jwt) {
        // Parses the JWT token and retrieves claims, specifically the 'email' claim
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // Sets the key used for parsing and validating the token
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return String.valueOf(claims.get("email")); // Returns the 'email' claim from JWT
    }
}
