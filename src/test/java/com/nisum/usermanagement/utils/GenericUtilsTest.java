package com.nisum.usermanagement.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericUtilsTest {

    @Test
    void extractBearer_validToken() {
        String authHeader = "Bearer validToken123";
        String token = GenericUtils.extractBearer(authHeader);
        assertEquals("validToken123", token);
    }

    @Test
    void extractBearer_nullAuthHeader() {
        String authHeader = null;
        String token = GenericUtils.extractBearer(authHeader);
        assertNull(token);
    }

    @Test
    void extractBearer_blankAuthHeader() {
        String authHeader = "   ";
        String token = GenericUtils.extractBearer(authHeader);
        assertNull(token);
    }

    @Test
    void extractBearer_invalidPrefix() {
        String authHeader = "Basic invalidToken";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            GenericUtils.extractBearer(authHeader);
        });
        assertEquals("Authorization debe ser Bearer <token>", exception.getMessage());
    }

    @Test
    void extractBearer_emptyToken() {
        String authHeader = "Bearer ";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            GenericUtils.extractBearer(authHeader);
        });
        assertEquals("Token Bearer vacío", exception.getMessage());
    }
}
