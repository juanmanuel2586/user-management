package com.nisum.usermanagement.service.impl;

import com.nisum.usermanagement.domain.Phone;
import com.nisum.usermanagement.domain.User;
import com.nisum.usermanagement.dto.PhoneDto;
import com.nisum.usermanagement.dto.UserDto;
import com.nisum.usermanagement.exception.NotFoundException;
import com.nisum.usermanagement.repository.UserRepository;
import com.nisum.usermanagement.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDto getById(String id) {
        User user = userRepository.findByIdWithPhones(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getByEmail(String email) {
        User user = userRepository.findByEmailWithPhones(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + email));
        return toDto(user);
    }

    /* ================== Mapping ================== */

    private UserDto toDto(User u) {
        // si usas Set<Phone>, ordena para respuesta estable (ej. por number)
        List<PhoneDto> phones = u.getPhones().stream()
                .sorted(Comparator.comparing(Phone::getNumber))
                .map(p -> new PhoneDto(p.getNumber(), p.getCityCode(), p.getCountryCode()))
                .toList();

        return new UserDto(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.isActive(),
                u.getCreated(),
                u.getModified(),
                u.getLastLogin(),
                u.getToken(),
                phones
        );
    }
}

