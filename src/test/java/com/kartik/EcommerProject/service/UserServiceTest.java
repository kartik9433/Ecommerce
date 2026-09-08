package com.kartik.EcommerProject.service;

import com.kartik.EcommerProject.exception.DuplicateResourceException;
import com.kartik.EcommerProject.model.User;
import com.kartik.EcommerProject.repo.UserRepository;
import com.kartik.EcommerProject.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Kartik")
                .email("kartik@example.com")
                .password("encoded-password")
                .build();

        userDetails = org.springframework.security.core.userdetails.User
                .withUsername("kartik@example.com")
                .password("encoded-password")
                .roles("USER")
                .build();
    }

    @Test
    void register_shouldThrowDuplicateResourceException_whenEmailExists() {
        when(userRepository.findByEmail("kartik@example.com")).thenReturn(Optional.of(user));

        assertThrows(DuplicateResourceException.class, () ->
                userService.register(com.kartik.EcommerProject.dto.RegisterRequest.builder()
                        .name("Kartik")
                        .email("kartik@example.com")
                        .password("password123")
                        .build()));

        verify(userRepository, never()).save(any());
    }

    @Test
    void register_shouldSaveUserAndReturnToken() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("jwt-token");

        var result = userService.register(com.kartik.EcommerProject.dto.RegisterRequest.builder()
                .name("Kartik")
                .email("kartik@example.com")
                .password("password123")
                .build());

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
        assertEquals("kartik@example.com", result.getEmail());
        assertEquals("Kartik", result.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_shouldReturnToken() {
        when(userRepository.findByEmail("kartik@example.com")).thenReturn(Optional.of(user));
        when(userDetailsService.loadUserByUsername("kartik@example.com")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("jwt-token");

        var result = userService.login(com.kartik.EcommerProject.dto.LoginRequest.builder()
                .email("kartik@example.com")
                .password("password123")
                .build());

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
        verify(authenticationManager).authenticate(any());
    }
}
