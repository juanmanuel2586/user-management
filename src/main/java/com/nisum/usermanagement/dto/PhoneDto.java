package com.nisum.usermanagement.dto;

public record PhoneDto(
        String number,
        String cityCode,
        String countryCode
) {}
