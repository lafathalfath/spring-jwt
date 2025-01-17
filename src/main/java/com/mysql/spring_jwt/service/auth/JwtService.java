package com.mysql.spring_jwt.service.auth;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mysql.spring_jwt.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${server.secret-key}")
    private String SECRET_KEY;
        
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

    public Claims extractAllClaims(String token) {
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
            .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
            .signWith(getSignInKey())
            .compact();
        return token;
    }

    public String refreshToken(String token, User user) {
        if (token != null && token.startsWith("Bearer ")) token = token.substring(7);
        // if (!isValid(token, user)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token");
        if (!isValid(token, user)) return null;
        return generateToken(user);
    }

    public Integer extractUserId(String token) {
        if (token != null && token.startsWith("Bearer ")) token = token.substring(7);
        return extractClaim(token, claim -> claim.get("id", Integer.class));
    }

}
