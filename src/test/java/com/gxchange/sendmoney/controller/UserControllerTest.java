package com.gxchange.sendmoney.controller;

import com.gxchange.sendmoney.dto.UserDto;
import com.gxchange.sendmoney.model.Role;
import com.gxchange.sendmoney.repository.RoleRepository;
import com.gxchange.sendmoney.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static java.util.Collections.singleton;

class UserControllerTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private RoleRepository roleRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should register user successfully")
    void registerSuccess() {
        UserDto userDto = new UserDto();
        userDto.setName("John");
        userDto.setEmail("john@example.com");
        userDto.setPhoneNumber("09171234567");
        userDto.setPassword("secret123");

        Role mockRole = new Role();
        mockRole.setName("ROLE_UNVERIFIED");

        when(userRepo.existsByEmail("john@example.com")).thenReturn(false);
        when(userRepo.existsByPhoneNumber("09171234567")).thenReturn(false);
        when(roleRepo.findByName("ROLE_UNVERIFIED")).thenReturn(mockRole);
        when(passwordEncoder.encode("secret123")).thenReturn("hashed-password");

        ResponseEntity<?> response = userController.register(userDto);

        verify(userRepo).save(any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    @DisplayName("Should reject if email already exists")
    void registerFailsOnDuplicateEmail() {
        UserDto userDto = new UserDto();
        userDto.setEmail("existing@example.com");
        userDto.setPhoneNumber("09171234567");

        when(userRepo.existsByEmail("existing@example.com")).thenReturn(true);

        ResponseEntity<?> response = userController.register(userDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
        verify(userRepo, never()).save(any());
    }

    @Test
    @DisplayName("Should reject if phone number already exists")
    void registerFailsOnDuplicatePhone() {
        UserDto userDto = new UserDto();
        userDto.setEmail("john@example.com");
        userDto.setPhoneNumber("09171234567");

        when(userRepo.existsByEmail("john@example.com")).thenReturn(false);
        when(userRepo.existsByPhoneNumber("09171234567")).thenReturn(true);

        ResponseEntity<?> response = userController.register(userDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Phone number already exists", response.getBody());
        verify(userRepo, never()).save(any());
    }
}
