package com.nisum.usermanagement.service.impl;

import com.nisum.usermanagement.domain.Phone;
import com.nisum.usermanagement.domain.User;
import com.nisum.usermanagement.dto.UserDto;
import com.nisum.usermanagement.dto.request.PhoneRequest;
import com.nisum.usermanagement.dto.request.UserRequest;
import com.nisum.usermanagement.exception.EmailConflictExpection;
import com.nisum.usermanagement.exception.NotFoundException;
import com.nisum.usermanagement.repository.UserRepository;
import com.nisum.usermanagement.utils.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock 
	private JwtUtil jwtUtil;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getById_found() {
		String id = UUID.randomUUID().toString();
		User user = mockUser(id);
		when(userRepository.findByIdWithPhones(id)).thenReturn(Optional.of(user));
		UserDto dto = userService.getById(id);
		assertEquals(id, dto.id());
		assertEquals(user.getEmail(), dto.email());
		assertEquals(user.getPhones().size(), dto.phones().size());
	}

	@Test
	void getById_notFound() {
		String id = UUID.randomUUID().toString();
		when(userRepository.findByIdWithPhones(id)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> userService.getById(id));
	}

	@Test
	void getByEmail_found() {
		String email = "test@example.com";
		User user = mockUser(UUID.randomUUID().toString());
		when(userRepository.findByEmailWithPhones(email)).thenReturn(Optional.of(user));
		UserDto dto = userService.getByEmail(email);
		assertEquals(email, dto.email());
	}

	@Test
	void getByEmail_notFound() {
		String email = "notfound@example.com";
		when(userRepository.findByEmailWithPhones(email)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> userService.getByEmail(email));
	}

	@Test
	void create_success() {
		UserRequest request = mock(UserRequest.class);
		when(request.email()).thenReturn("new@example.com");
		when(request.name()).thenReturn("New User");
		when(request.password()).thenReturn("password");
		when(request.phones()).thenReturn(Arrays.asList(
				new PhoneRequest("123", "1", "57"),
				new PhoneRequest("456", "2", "34")
		));
		when(userRepository.existsByEmailIgnoreCase("new@example.com")).thenReturn(false);
		when(passwordEncoder.encode("password")).thenReturn("hashed");
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User savedUser = mockUser(UUID.randomUUID().toString());
		when(userRepository.save(userCaptor.capture())).thenReturn(savedUser);
        when(jwtUtil.generate(eq("new@example.com"), anyMap())).thenReturn("jwt-token");
		UserDto dto = userService.create(request);
		assertEquals(savedUser.getEmail(), dto.email());
		assertEquals(savedUser.getPhones().size(), dto.phones().size());
		verify(userRepository).save(any(User.class));
	}

	@Test
	void create_emailExists() {
		UserRequest request = mock(UserRequest.class);
		when(request.email()).thenReturn("exists@example.com");
		when(userRepository.existsByEmailIgnoreCase("exists@example.com")).thenReturn(true);
		assertThrows(EmailConflictExpection.class, () -> userService.create(request));
	}

	private User mockUser(String id) {
		User user = mock(User.class);
		when(user.getId()).thenReturn(id);
		when(user.getName()).thenReturn("Test User");
		when(user.getEmail()).thenReturn("test@example.com");
		when(user.isActive()).thenReturn(true);
		when(user.getCreated()).thenReturn(Instant.now());
		when(user.getModified()).thenReturn(Instant.now());
		when(user.getLastLogin()).thenReturn(Instant.now());
		when(user.getToken()).thenReturn("token");
		Phone phone1 = mock(Phone.class);
		when(phone1.getNumber()).thenReturn("123");
		when(phone1.getCityCode()).thenReturn("1");
		when(phone1.getCountryCode()).thenReturn("57");
		Phone phone2 = mock(Phone.class);
		when(phone2.getNumber()).thenReturn("456");
		when(phone2.getCityCode()).thenReturn("2");
		when(phone2.getCountryCode()).thenReturn("34");
		when(user.getPhones()).thenReturn(new HashSet<>(Arrays.asList(phone1, phone2)));
		return user;
	}
}
