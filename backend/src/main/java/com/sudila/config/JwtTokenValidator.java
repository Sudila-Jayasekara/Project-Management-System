package com.sudila.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    // Validates JWT token for subsequent requests to secure endpoints

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Extract JWT token from Authorization header
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null) {
            // Remove "Bearer " prefix from token
            jwt = jwt.substring(7);

            try {
                // Parse JWT token using the secret key
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                // Extract email and authorities from JWT claims
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                // Convert authorities string to list of GrantedAuthority
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // Create authentication object with email and authorities
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);

                // Set the authentication object in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Throw exception if token is invalid or parsing fails
                throw new BadCredentialsException("Invalid token");
            }
        }

        // Continue filter chain processing
        filterChain.doFilter(request, response);
    }
}
