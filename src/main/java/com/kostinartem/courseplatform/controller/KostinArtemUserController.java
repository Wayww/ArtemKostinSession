package com.kostinartem.courseplatform.controller;

import com.kostinartem.courseplatform.dto.KostinArtemRegisterRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemUserResponseDto;
import com.kostinartem.courseplatform.service.KostinArtemUserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class KostinArtemUserController {

    private final KostinArtemUserService userService;

    @GetMapping
    public List<KostinArtemUserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public KostinArtemUserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public KostinArtemUserResponseDto updateUser(@PathVariable Long id,
                                                 @Valid @RequestBody KostinArtemRegisterRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
