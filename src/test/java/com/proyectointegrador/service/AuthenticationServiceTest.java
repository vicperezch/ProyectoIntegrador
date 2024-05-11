package com.proyectointegrador.service;

import com.proyectointegrador.constant.Role;
import com.proyectointegrador.controller.LoginDto;
import com.proyectointegrador.controller.RegisterDto;
import com.proyectointegrador.model.User;
import com.proyectointegrador.repository.UserRepository;
import com.proyectointegrador.security.JwtUtil;
import com.proyectointegrador.security.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void testRegisterWithValidInformation() {
        RegisterDto registerDto = new RegisterDto("test@test.com", "password", "test");

        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(jwtUtil.generateToken(any(UserDetailsImpl.class), eq(Role.USER))).thenReturn("token");

        String result = authenticationService.register(registerDto);

        assertEquals("token", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLoginWithCreatedUser() {
        LoginDto loginDto = new LoginDto("test@test.com", "password");
        User user = new User("test", "test@test.com", "encodedPassword", "USER");

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any(UserDetailsImpl.class), eq(Role.USER))).thenReturn("token");

        String result = authenticationService.login(loginDto);

        assertEquals("token", result);
    }

    @Test
    public void testRegisterWithExistingUser() {
        RegisterDto registerDto = new RegisterDto("test@test.com", "password", "test");

        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> authenticationService.register(registerDto));
    }

    @Test
    public void testLoginThrowsUsernameNotFoundException() {
        LoginDto loginDto = new LoginDto("test@test.com", "password");

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.login(loginDto));
    }
}
