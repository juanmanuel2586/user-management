package com.nisum.usermanagement.controller;

import com.nisum.usermanagement.dto.UserDto;
import com.nisum.usermanagement.dto.request.UserRequest;
import com.nisum.usermanagement.service.UserService;
import com.nisum.usermanagement.utils.GenericUtils;

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

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserRequest request,
                                        @RequestHeader(value = "Authorization", required = true) String authHeader) {
        String token = GenericUtils.extractBearer(authHeader);
        validarPassword(request.password());
        UserDto dto = userService.create(request, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    
    private void validarPassword(String password) {
        if (!Pattern.matches(passwordRegex, password)) {
            throw new IllegalArgumentException(passwordMessageError);
        }
    }
}
