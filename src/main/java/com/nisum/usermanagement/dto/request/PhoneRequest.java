package com.nisum.usermanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PhoneRequest(
    
        @Schema(description = "Número de teléfono",
            example = "65985487",
            maxLength = 15)
        @NotBlank(message = "El número de telefono es obligatorio")
        @Size(max = 15, message = "El número de telefono no debe superar los 15 caracteres")
        String number,

        @Schema(description = "Código de ciudad",
            example = "9",
            maxLength = 10)
        @NotBlank(message = "El código de ciudad es obligatorio")
        @Size(max = 10, message = "El código de ciudad no debe superar los 10 caracteres") 
        String cityCode,

        @Schema(description = "Código de país",
            example = "56",
            maxLength = 10)
        @NotBlank(message = "El código de país es obligatorio") 
        @Size(max = 10, message = "El código de país no debe superar los 10 caracteres") 
        String countryCode
) {}