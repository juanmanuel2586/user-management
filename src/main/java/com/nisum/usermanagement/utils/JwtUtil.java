package com.nisum.usermanagement.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtil {

    
    private final SecretKey key;
    private final String issuer;
    private final long expirationMillis;


    public JwtUtil(@Value("${app.jwt.secret}") String secret, 
                    @Value("${app.jwt.issuer:user-management}") String issuer, 
                    @Value("${app.jwt.expiration-millis:3600000}") long expirationMillis) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
        this.expirationMillis = expirationMillis;
    }

    /** Genera un JWT HS256 con subject y claims opcionales */
    public String generate(String subject, Map<String, Object> claims) {
        long now = System.currentTimeMillis();

        var token = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(subject)      
                .issuer(issuer)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMillis))
                .claims(claims)
                .signWith(key)
                .compact();
        log.debug("JWT generado para subject {}: {}", subject, token);
        return token;
    }   
}
