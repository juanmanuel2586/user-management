package com.nisum.usermanagement.repository;

import com.nisum.usermanagement.domain.User;
import com.nisum.usermanagement.domain.Phone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSaveUser_thenFindByEmailIgnoreCaseWorks() {
        User u = new User(UUID.randomUUID().toString(), "Juan", "juan@demo.com", "hash", "token123");
        userRepository.save(u);

        Optional<User> found = userRepository.findByEmailIgnoreCase("JUAN@DEMO.COM");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("juan@demo.com");
    }

    @Test
    void whenUserWithPhones_thenFindByIdWithPhonesFetchesPhones() {
        User u = new User(UUID.randomUUID().toString(), "Maria", "maria@demo.com", "hash", "token456");

        Phone p = new Phone("12345", "1", "57", u);
        u.addPhone(p);

        userRepository.save(u);

        Optional<User> found = userRepository.findByIdWithPhones(u.getId().toString());

        assertThat(found).isPresent();
        assertThat(found.get().getPhones()).hasSize(1);
    }
}

