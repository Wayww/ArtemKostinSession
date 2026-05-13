package com.kostinartem.courseplatform.controller;

import com.kostinartem.courseplatform.dto.KostinArtemCourseRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemCourseResponseDto;
import com.kostinartem.courseplatform.service.KostinArtemCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class KostinArtemCourseController {

    private final KostinArtemCourseService courseService;

    @GetMapping
    public Page<KostinArtemCourseResponseDto> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String level) {
        return courseService.getAllCourses(page, size, sortBy, direction, keyword, category, level);
    }

    @GetMapping("/{id}")
    public KostinArtemCourseResponseDto getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<KostinArtemCourseResponseDto> createCourse(
            @Valid @RequestBody KostinArtemCourseRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(requestDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public KostinArtemCourseResponseDto updateCourse(@PathVariable Long id,
                                                     @Valid @RequestBody KostinArtemCourseRequestDto requestDto) {
        return courseService.updateCourse(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
