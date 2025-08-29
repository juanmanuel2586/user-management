package com.nisum.usermanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PhoneRequest(
    
        @NotBlank(message = "El número de telefono es obligatorio")
        @Size(max = 15, message = "El número de telefono no debe superar los 15 caracteres")
        String number,

        @NotBlank(message = "El código de ciudad es obligatorio")
        @Size(max = 10, message = "El código de ciudad no debe superar los 10 caracteres") 
        String cityCode,

        @NotBlank(message = "El código de país es obligatorio") 
        @Size(max = 10, message = "El código de país no debe superar los 10 caracteres") 
        String countryCode
) {}