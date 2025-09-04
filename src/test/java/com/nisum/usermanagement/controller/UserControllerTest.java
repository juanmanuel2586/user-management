package com.nisum.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.usermanagement.dto.UserDto;
import com.nisum.usermanagement.dto.request.PhoneRequest;
import com.nisum.usermanagement.dto.request.UserRequest;
import com.nisum.usermanagement.exception.GlobalExceptionHandler;
import com.nisum.usermanagement.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva seguridad para tests
@Import(GlobalExceptionHandler.class) 
@TestPropertySource(properties = {
    "app.password.regex=^(?=.*[A-Za-z])(?=.*\\d).{6,}$",
    "app.password.message=La contraseña debe tener al menos 6 caracteres, incluyendo una letra y un número"
})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    private UserDto sampleUserDto() {
        return new UserDto(
                UUID.randomUUID().toString(),
                "Juan Perez",
                "juan.perez@example.com",
                true,
                Instant.parse("2025-08-29T12:00:00Z"),
                Instant.parse("2025-08-29T12:00:00Z"),
                Instant.parse("2025-08-29T12:00:00Z"),
                "jwt-or-uuid-token",
                List.of() 
        );
    }


    @Test
    @DisplayName("GET /api/user/{id} → 200 OK")
    void getById_ok() throws Exception {
        Mockito.when(userService.getById("1111"))
                .thenReturn(sampleUserDto());

        mockMvc.perform(get("/api/user/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"));
    }

    @Test
    @DisplayName("GET /api/user?email=... → 200 OK")
    void getByEmail_ok() throws Exception {
        Mockito.when(userService.getByEmail("juan.perez@example.com"))
                .thenReturn(sampleUserDto());

        mockMvc.perform(get("/api/user")
                        .queryParam("email", "juan.perez@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Perez"));
    }
 /*
    @Test
    @DisplayName("POST /api/user con Authorization y password válida → 201 Created")
    void create_ok() throws Exception {
        var req = validRequest();
        var dto = sampleUserDto();

        Mockito.when(userService.create(Mockito.any(UserRequest.class), eq("valid-token")))
                .thenReturn(dto);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid-token")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("11111111-1111-1111-1111-111111111111"))
                .andExpect(jsonPath("$.token").value("jwt-or-uuid-token"));
    }
*/

    @Test
    @DisplayName("POST /api/user con password inválida → 400 Bad Request con mensaje configurado")
    void create_invalidPassword_400() throws Exception {
        var badReq = new UserRequest(
                "Juan Perez",
                "juan.perez@example.com",
                "1122",
                List.of(new PhoneRequest("1234567", "1", "57"))
        );

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid-token")
                        .content(objectMapper.writeValueAsString(badReq)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje")
                        .value("La contraseña debe tener al menos 6 caracteres, incluyendo una letra y un número"));
    }
}
