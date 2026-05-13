package com.kostinartem.courseplatform.service;

import com.kostinartem.courseplatform.dto.KostinArtemAuthResponseDto;
import com.kostinartem.courseplatform.dto.KostinArtemLoginRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemRegisterRequestDto;
import com.kostinartem.courseplatform.entity.KostinArtemUser;
import com.kostinartem.courseplatform.exception.KostinArtemConflictException;
import com.kostinartem.courseplatform.repository.KostinArtemUserRepository;
import com.kostinartem.courseplatform.security.KostinArtemJwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KostinArtemAuthService {

    private final KostinArtemUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final KostinArtemJwtService jwtService;
    private final KostinArtemAsyncService asyncService;

    public KostinArtemAuthResponseDto register(KostinArtemRegisterRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            log.error("Register failed. Email already exists: {}", requestDto.getEmail());
            throw new KostinArtemConflictException("User with this email already exists");
        }

        KostinArtemUser user = new KostinArtemUser();
        user.setFullName(requestDto.getFullName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(requestDto.getRole());

        KostinArtemUser savedUser = userRepository.save(user);
        asyncService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getFullName());
        log.info("User registered: {}", savedUser.getEmail());

        User securityUser = new User(savedUser.getEmail(), savedUser.getPassword(), java.util.List.of());
        String token = jwtService.generateToken(securityUser);

        return new KostinArtemAuthResponseDto(token);
    }

    public KostinArtemAuthResponseDto login(KostinArtemLoginRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );

        KostinArtemUser user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new KostinArtemConflictException("User not found"));
        log.info("User logged in: {}", user.getEmail());

        User securityUser = new User(user.getEmail(), user.getPassword(), java.util.List.of());
        String token = jwtService.generateToken(securityUser);

        return new KostinArtemAuthResponseDto(token);
    }
}
