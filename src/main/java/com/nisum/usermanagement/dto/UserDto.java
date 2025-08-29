package com.nisum.usermanagement.dto;

import java.time.Instant;
import java.util.List;

public record UserDto(
        String id,
        String name,
        String email,
        boolean isActive,
        Instant created,
        Instant modified,
        Instant lastLogin,
        String token,
        List<PhoneDto> phones
) {}
