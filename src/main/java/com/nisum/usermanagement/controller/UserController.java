package com.nisum.usermanagement.controller;

import com.nisum.usermanagement.dto.UserDto;
import com.nisum.usermanagement.dto.request.UserRequest;
import com.nisum.usermanagement.exception.GlobalExceptionHandler.ErrorResponse;
import com.nisum.usermanagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/user", consumes = "application/json", produces = "application/json")
public class UserController {

    private final UserService userService;
    
    @Value("${app.password.regex}")
    private String passwordRegex;    
    
    @Value("${app.password.message}")
    private String passwordMessageError;


    public UserController(UserService users) {
        this.userService = users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<UserDto> getByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @Operation(summary = "Crear usuario",
    description = "Crea un usuario con sus teléfonos.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Usuario creado con Exito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos o no cumple con el formato esperado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Email proporcionado ya esta registrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserRequest request) {
        validarPassword(request.password());
        UserDto dto = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    
    private void validarPassword(String password) {
        if (!Pattern.matches(passwordRegex, password)) {
            throw new IllegalArgumentException(passwordMessageError);
        }
    }
}
