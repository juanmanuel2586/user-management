package com.nisum.usermanagement.service.impl;

import com.nisum.usermanagement.domain.Phone;
import com.nisum.usermanagement.domain.User;
import com.nisum.usermanagement.dto.PhoneDto;
import com.nisum.usermanagement.dto.UserDto;
import com.nisum.usermanagement.dto.request.UserRequest;
import com.nisum.usermanagement.exception.EmailConflictExpection;
import com.nisum.usermanagement.exception.NotFoundException;
import com.nisum.usermanagement.repository.UserRepository;
import com.nisum.usermanagement.service.UserService;
import com.nisum.usermanagement.utils.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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

    @Transactional
    public UserDto create(UserRequest request) {
        // Validar email único
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new EmailConflictExpection("El correo ya está registrado");
        }

        String hashed = passwordEncoder.encode(request.password());
        String id = UUID.randomUUID().toString();

        Map<String, Object> claims = Map.of("name", request.name());
        String token = jwtUtil.generate(request.email(), claims);
        User user = new User(
                id,
                request.name(),
                request.email(),
                hashed,
                token
        );
        if (request.phones() != null) {
            request.phones().forEach(p -> {
                Phone phone = new Phone(p.number(), p.cityCode(), p.countryCode(), user);
                user.addPhone(phone);
            });
        }

        User saved = userRepository.save(user);
        return toDto(saved);
    }
}

