package com.kostinartem.courseplatform.service;

import com.kostinartem.courseplatform.dto.KostinArtemEnrollmentResponseDto;
import com.kostinartem.courseplatform.entity.KostinArtemCourse;
import com.kostinartem.courseplatform.entity.KostinArtemEnrollment;
import com.kostinartem.courseplatform.entity.KostinArtemUser;
import com.kostinartem.courseplatform.exception.KostinArtemNotFoundException;
import com.kostinartem.courseplatform.repository.KostinArtemCourseRepository;
import com.kostinartem.courseplatform.repository.KostinArtemEnrollmentRepository;
import com.kostinartem.courseplatform.repository.KostinArtemUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KostinArtemEnrollmentService {

    private final KostinArtemEnrollmentRepository enrollmentRepository;
    private final KostinArtemUserRepository userRepository;
    private final KostinArtemCourseRepository courseRepository;
    private final KostinArtemAsyncService asyncService;

    public KostinArtemEnrollmentResponseDto enrollUser(Long userId, Long courseId, String status) {
        KostinArtemUser user = userRepository.findById(userId)
                .orElseThrow(() -> new KostinArtemNotFoundException("User not found with id: " + userId));

        KostinArtemCourse course = courseRepository.findById(courseId)
                .orElseThrow(() -> new KostinArtemNotFoundException("Course not found with id: " + courseId));

        KostinArtemEnrollment enrollment = new KostinArtemEnrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setStatus(status);

        KostinArtemEnrollment savedEnrollment = enrollmentRepository.save(enrollment);
        asyncService.logEnrollment(userId, courseId);
        asyncService.calculateFakeStatistics(courseId);
        log.info("User enrolled: userId={}, courseId={}", userId, courseId);

        return mapToResponse(savedEnrollment);
    }

    public List<KostinArtemEnrollmentResponseDto> getUserEnrollments(Long userId) {
        return enrollmentRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private KostinArtemEnrollmentResponseDto mapToResponse(KostinArtemEnrollment enrollment) {
        KostinArtemEnrollmentResponseDto dto = new KostinArtemEnrollmentResponseDto();
        dto.setId(enrollment.getId());
        dto.setStatus(enrollment.getStatus());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        dto.setUserId(enrollment.getUser().getId());
        dto.setCourseId(enrollment.getCourse().getId());
        return dto;
    }
}
