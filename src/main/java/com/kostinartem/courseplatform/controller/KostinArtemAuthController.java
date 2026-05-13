package com.kostinartem.courseplatform.controller;

import com.kostinartem.courseplatform.dto.KostinArtemAuthResponseDto;
import com.kostinartem.courseplatform.dto.KostinArtemLoginRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemRegisterRequestDto;
import com.kostinartem.courseplatform.service.KostinArtemAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KostinArtemAuthController {

    private final KostinArtemAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<KostinArtemAuthResponseDto> register(
            @Valid @RequestBody KostinArtemRegisterRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<KostinArtemAuthResponseDto> login(
            @Valid @RequestBody KostinArtemLoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
