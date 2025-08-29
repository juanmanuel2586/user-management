package com.nisum.usermanagement.controller;

import com.nisum.usermanagement.dto.UserDto;
import com.nisum.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/user", consumes = "application/json", produces = "application/json")
public class UserController {

    private final UserService userService;

    public UserController(UserService users) {
        this.userService = users;
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    // GET por email: /api/user?email=juan.perez@example.com
    @GetMapping
    public ResponseEntity<UserDto> getByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }
}
