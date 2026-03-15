package com.example.auth_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

/// Jwtトークンを生成、検証するbean
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //jwtトークン生成
    public String generateToken(UUID id) {
        String token = Jwts.builder()
                           .setSubject(id.toString())
                           .setExpiration(new Date(System.currentTimeMillis() + expiration))
                           .signWith(getSigningKey())
                           .compact();

        return token;
    }

    //トークンからID取得
    public UUID extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getSigningKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

        return UUID.fromString(claims.getSubject());
    }

    //トークン検証
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

            return true;

        } catch (Exception e){
                return false;
            }
    }
}
