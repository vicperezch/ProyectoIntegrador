package com.proyectointegrador.service;

import com.proyectointegrador.constant.Role;
import com.proyectointegrador.controller.LoginDto;
import com.proyectointegrador.controller.RegisterDto;
import com.proyectointegrador.model.User;
import com.proyectointegrador.repository.UserRepository;
import com.proyectointegrador.security.JwtUtil;
import com.proyectointegrador.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = new User(registerDto.getName(), registerDto.getEmail(), encodedPassword, String.valueOf(Role.USER));

        userRepository.save(user);
        return jwtUtil.generateToken(UserDetailsImpl.build(user), Role.USER);
    }

    public String login(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        Optional<User> optionalUser = userRepository.findByEmail(loginDto.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            return jwtUtil.generateToken(UserDetailsImpl.build(user), Role.USER);
        }

        throw new UsernameNotFoundException("Failed login");
    }
}
