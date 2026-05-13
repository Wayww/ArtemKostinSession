package com.kostinartem.courseplatform.service;

import com.kostinartem.courseplatform.dto.KostinArtemRegisterRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemUserResponseDto;
import com.kostinartem.courseplatform.entity.KostinArtemUser;
import com.kostinartem.courseplatform.exception.KostinArtemNotFoundException;
import com.kostinartem.courseplatform.repository.KostinArtemUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KostinArtemUserService {

    private final KostinArtemUserRepository userRepository;

    public List<KostinArtemUserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public KostinArtemUserResponseDto getUserById(Long id) {
        KostinArtemUser user = userRepository.findById(id)
                .orElseThrow(() -> new KostinArtemNotFoundException("User not found with id: " + id));
        return mapToResponse(user);
    }

    public KostinArtemUserResponseDto updateUser(Long id, KostinArtemRegisterRequestDto requestDto) {
        KostinArtemUser user = userRepository.findById(id)
                .orElseThrow(() -> new KostinArtemNotFoundException("User not found with id: " + id));

        user.setFullName(requestDto.getFullName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setRole(requestDto.getRole());

        return mapToResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        KostinArtemUser user = userRepository.findById(id)
                .orElseThrow(() -> new KostinArtemNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    private KostinArtemUserResponseDto mapToResponse(KostinArtemUser user) {
        KostinArtemUserResponseDto dto = new KostinArtemUserResponseDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
