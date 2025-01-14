package com.mysql.spring_jwt.service.auth;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mysql.spring_jwt.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private String SECRET_KEY = "167a0cc18d5b5e545204d0c1bd76ee9671a5ab8e9abef1af705d6b2253ad791b";
        
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
        
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    public String generateToken(User user) {
        String token = Jwts
            .builder()
            .subject(user.getUsername())
            .claim("id", user.getId())
            .claim("username", user.getUsername())
            .claim("email", user.getEmail())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(getSignInKey())
            .compact();
        return token;
    }

    public Integer extractUserId(String token) {
        if (token != null && token.startsWith("Bearer ")) token = token.substring(7);
        return extractClaim(token, claim -> claim.get("id", Integer.class));
    }

}
