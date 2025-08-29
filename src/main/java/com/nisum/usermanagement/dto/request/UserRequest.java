package com.nisum.usermanagement.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
        String name,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de email inválido")
        @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "El correo debe cumplir el formato mail@dominio.extension"
        )
        @Size(max = 320, message = "El correo no puede superar los 320 caracteres")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password,
        
        @NotNull(message = "Los teléfonos son obligatorios")
        @Size(min = 1, message = "Debe haber al menos un teléfono")
        @Valid
        List<PhoneRequest> phones
) {}
