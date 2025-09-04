package com.nisum.usermanagement.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserRequest(

        @Schema(description = "Nombre completo del usuario",
            example = "Juan Vasquez",
            maxLength = 100)
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
        String name,
        
        @Schema(description = "Correo electrónico",
            format = "email",
            example = "juan.vasquez@example.cl",
            maxLength = 320)
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de email inválido")
        @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "El correo debe cumplir el formato mail@dominio.extension"
        )
        @Size(max = 320, message = "El correo no puede superar los 320 caracteres")
        String email,

        @Schema(description = "Contraseña del usuario. Mínimo 8 caracteres, al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.",
            example = "P@ssw0rd!",
            minLength = 8)
        @NotBlank(message = "La contraseña es obligatoria")
        String password,
        
        @Schema(description = "Lista de teléfonos del usuario")
        @NotNull(message = "Los teléfonos son obligatorios")
        @Size(min = 1, message = "Debe haber al menos un teléfono")
        @Valid
        List<PhoneRequest> phones
) {}
