package com.nisum.usermanagement.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtilTest {

     private JwtUtil jwtUtil;
    private String secret = "mi-clave-super-secreta-que-tiene-mas-de-32-caracteres!";
    private String issuer = "user-management";
    private long expiration = 3600000L; // 1h

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secret, issuer, expiration);
    }

    @Test
    void generateToken_containsSubjectAndClaims() {
        // arrange
        String subject = "user@example.com";
        Map<String, Object> claims = Map.of("role", "ADMIN", "name", "Juan");

        // act
        String token = jwtUtil.generate(subject, claims);

        // assert
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // parsear con la misma key
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Claims parsed = Jwts.parser()
            .verifyWith(key)       
            .requireIssuer(issuer)
            .build()
            .parseSignedClaims(token) 
            .getPayload();

        assertEquals(subject, parsed.getSubject());
        assertEquals("ADMIN", parsed.get("role", String.class));
        assertEquals("Juan", parsed.get("name", String.class));
        assertTrue(parsed.getExpiration().getTime() > System.currentTimeMillis());
    }
}
