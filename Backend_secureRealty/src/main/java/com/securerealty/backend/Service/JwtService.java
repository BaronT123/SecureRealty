package com.securerealty.backend.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.securerealty.backend.Model.User;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));

    }
    public String generateToken(User user) {

        return Jwts.builder()

                .subject(user.getName())

                .issuedAt(new Date())

                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))

                .signWith(getSigningKey())

                .compact();

    }
    private Claims extractAllClaims(String token) {

	    return Jwts.parser()
	            .verifyWith(getSigningKey())
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();

	}
	public String extractUsername(String token) {

	    return extractAllClaims(token).getSubject();

	}
	public Date extractExpiration(String token) {

        return extractAllClaims(token).getExpiration();

    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());

    }

    public boolean validateToken(String token, UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);

    }

}