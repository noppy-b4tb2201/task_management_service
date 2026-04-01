package com.example.auth_service.service;

import com.example.auth_service.dto.request.LoginRequestDto;
import com.example.auth_service.dto.request.RegisterRequestDto;
import com.example.auth_service.entity.User;
import com.example.auth_service.exception.InvalidPasswordException;
import com.example.auth_service.exception.UserAlreadyExistsException;
import com.example.auth_service.exception.UserDontExistException;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @InjectMocks
    AuthService authService;

    @Test
    void register_Success() {

        String username = "user";
        String email = "a";
        String password = "abcdef";

        RegisterRequestDto request = RegisterRequestDto.builder()
                   .username(username)
                   .email(email)
                   .password(password)
                   .build();

        when(userRepository.existsByEmail(email)).thenReturn(false);

        authService.register(request);

        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void register_Failed() {

        String username = "user";
        String email = "a";
        String password = "abcdef";

        RegisterRequestDto request = RegisterRequestDto.builder()
                                                       .username(username)
                                                       .email(email)
                                                       .password(password)
                                                       .build();

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.register(request);
        });

        verify(userRepository, times(0)).save(any());

    }

    @Test
    void login_Success() {

        String email = "email";
        String password = "password";
        UUID id = UUID.randomUUID();

        LoginRequestDto request = new LoginRequestDto();
        request.setEmail(email);
        request.setPassword(password);

        User user = User.builder()
                        .id(id)
                        .email(email)
                        .passwordHash("hassedpassword")
                        .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.getPassword(), user.getPasswordHash())).thenReturn(true);

        when(jwtService.generateToken(id)).thenReturn("mocktoken");

        String token = authService.login(request);

        assertNotNull(token);
        assertEquals("mocktoken", token);
    }

    @Test
    void login_Failed() {

        String email = "email";
        String password = "password";
        UUID id = UUID.randomUUID();

        LoginRequestDto request = new LoginRequestDto();
        request.setEmail(email);
        request.setPassword(password);

        User user = User.builder()
                        .id(id)
                        .email(email)
                        .passwordHash("hassedpassword")
                        .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserDontExistException.class, () -> {
            authService.login(request);
        });

        verify(passwordEncoder, times(0)).matches(any(), any());
        verify(jwtService, times(0)).generateToken(any());

    }
}