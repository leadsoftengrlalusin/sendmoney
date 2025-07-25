package com.gxchange.sendmoney.controller;

import com.gxchange.sendmoney.dto.AuthRequest;
import com.gxchange.sendmoney.dto.AuthResponse;
import com.gxchange.sendmoney.model.User;
import com.gxchange.sendmoney.repository.UserRepository;
import com.gxchange.sendmoney.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("POST /api/auth/login - success")
    void testLoginSuccess() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setPhoneNumber("09171234567");
        request.setPassword("secret");

        User user = new User();
        user.setPhoneNumber("09171234567");
        user.setPassword("hashed-secret");

        when(userRepo.findByPhoneNumber("09171234567")).thenReturn(user);
        when(passwordEncoder.matches("secret", "hashed-secret")).thenReturn(true);
        when(jwtUtil.generateToken("09171234567")).thenReturn("mock-jwt-token");

        // Act
        AuthResponse response = authController.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());
    }

    @Test
    @DisplayName("POST /api/auth/login - invalid credentials")
    void testLoginFailure() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setPhoneNumber("09171234567");
        request.setPassword("wrong");

        User user = new User();
        user.setPhoneNumber("09171234567");
        user.setPassword("hashed-secret");

        when(userRepo.findByPhoneNumber("09171234567")).thenReturn(user);
        when(passwordEncoder.matches("wrong", "hashed-secret")).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.login(request);
        });

        assertEquals("Invalid phone number or password", exception.getMessage());
    }

    @Test
    @DisplayName("POST /api/auth/login - user not found")
    void testLoginUserNotFound() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setPhoneNumber("09170000000");
        request.setPassword("secret");

        when(userRepo.findByPhoneNumber("09170000000")).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.login(request);
        });

        assertEquals("Invalid phone number or password", exception.getMessage());
    }
}
