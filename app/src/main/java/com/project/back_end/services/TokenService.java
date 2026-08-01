package com.project.back_end.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    // A single symmetric HMAC key is generated once for the application lifecycle.
    private final Key signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Builds the symmetric signing key used for JWT creation and validation.
     */
    public Key getSigningKey() {
        return signingKey;
    }

    /**
     * Generates a short-lived JWT for the supplied email address.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                // Tokens expire after 24 hours.
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a Bearer token or raw JWT string.
     */
    public boolean validateToken(String token) {
        try {
            // Accept either "Bearer <token>" or a raw token value.
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
