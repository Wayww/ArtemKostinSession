package com.kostinartem.courseplatform.controller;

import com.kostinartem.courseplatform.dto.KostinArtemEnrollmentResponseDto;
import com.kostinartem.courseplatform.service.KostinArtemEnrollmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KostinArtemEnrollmentController {

    private final KostinArtemEnrollmentService enrollmentService;

    @PostMapping("/enrollments")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<KostinArtemEnrollmentResponseDto> enrollUser(
            @RequestParam Long userId,
            @RequestParam Long courseId,
            @RequestParam(defaultValue = "ENROLLED") String status) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enrollmentService.enrollUser(userId, courseId, status));
    }

    @GetMapping("/users/{userId}/enrollments")
    @PreAuthorize("hasRole('STUDENT')")
    public List<KostinArtemEnrollmentResponseDto> getUserEnrollments(@PathVariable Long userId) {
        return enrollmentService.getUserEnrollments(userId);
    }
}
