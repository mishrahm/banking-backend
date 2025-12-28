package com.bank.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // 🔐 Secret key (same key must be used for generation + validation)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    // Generate token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // 🔍 Extract email from token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // ⏳ Check token expiry
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // ✅ Validate token
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    // 🔒 Internal method
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}




/*
Token is cryptographically verified
If token is:
tampered ❌
expired ❌
fake ❌
→ request is rejected
 
 */
