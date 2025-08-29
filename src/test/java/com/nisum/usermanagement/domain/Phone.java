package com.nisum.usermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    @Test
    void testConstructorAndGetters() {
        User user = new User(); // Suponiendo que existe una clase User
        Phone phone = new Phone("123456789", "01", "56", user);

        assertEquals("123456789", phone.getNumber());
        assertEquals("01", phone.getCityCode());
        assertEquals("56", phone.getCountryCode());
        assertEquals(user, phone.getUser());
    }

    @Test
    void testSetters() {
        User user = new User(); // Suponiendo que existe una clase User
        Phone phone = new Phone();

        phone.setNumber("987654321");
        phone.setCityCode("02");
        phone.setCountryCode("57");
        phone.setUser(user);

        assertEquals("987654321", phone.getNumber());
        assertEquals("02", phone.getCityCode());
        assertEquals("57", phone.getCountryCode());
        assertEquals(user, phone.getUser());
    }

    @Test
    void testDefaultConstructor() {
        Phone phone = new Phone();
        assertNull(phone.getNumber());
        assertNull(phone.getCityCode());
        assertNull(phone.getCountryCode());
        assertNull(phone.getUser());
    }
}