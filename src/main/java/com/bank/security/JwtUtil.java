package com.bank.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	// 🔐 SECRET KEY (used to sign token)
    // In real apps → move to application.properties
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	// ⏰ Token validity (10 hours)
	private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
	
	// ✅ Generate JWT token
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)        // who the token belongs to
				.setIssuedAt(new Date())  // token creation time
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key)            // digital signature
				.compact();
	}

}
