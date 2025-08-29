package com.nisum.usermanagement.domain;

import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        String id = "123";
        String name = "Test User";
        String email = "test@example.com";
        String passwordHash = "hashedPassword";
        String token = "testToken";

        User user = new User(id, name, email, passwordHash, token);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(token, user.getToken());
        assertTrue(user.isActive());
    }

    @Test
    void testSetters() {
        User user = new User();

        user.setId("123");
        user.setName("Updated User");
        user.setEmail("updated@example.com");
        user.setPasswordHash("newHashedPassword");
        user.setToken("newToken");
        user.setActive(false);

        assertEquals("123", user.getId());
        assertEquals("Updated User", user.getName());
        assertEquals("updated@example.com", user.getEmail());
        assertEquals("newHashedPassword", user.getPasswordHash());
        assertEquals("newToken", user.getToken());
        assertFalse(user.isActive());
    }

    @Test
    void testAddPhone() {
        User user = new User();
        Phone phone = new Phone("123456789", "01", "56", null);

        user.addPhone(phone);

        Set<Phone> phones = user.getPhones();
        assertTrue(phones.contains(phone));
        assertEquals(user, phone.getUser());
    }

    @Test
    void testRemovePhone() {
        User user = new User();
        Phone phone = new Phone("123456789", "01", "56", user);

        user.addPhone(phone);
        user.removePhone(phone);

        Set<Phone> phones = user.getPhones();
        assertFalse(phones.contains(phone));
        assertNull(phone.getUser());
    }

    @Test
    void testPrePersist() {
        User user = new User();
        user.prePersist();

        assertNotNull(user.getLastLogin());
    }
}