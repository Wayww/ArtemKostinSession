package com.kostinartem.courseplatform.controller;

import com.kostinartem.courseplatform.dto.KostinArtemCourseRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemCourseResponseDto;
import com.kostinartem.courseplatform.service.KostinArtemCourseService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class KostinArtemCourseController {

    private final KostinArtemCourseService courseService;

    @GetMapping
    public List<KostinArtemCourseResponseDto> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public KostinArtemCourseResponseDto getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public ResponseEntity<KostinArtemCourseResponseDto> createCourse(
            @Valid @RequestBody KostinArtemCourseRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(requestDto));
    }

    @PutMapping("/{id}")
    public KostinArtemCourseResponseDto updateCourse(@PathVariable Long id,
                                                     @Valid @RequestBody KostinArtemCourseRequestDto requestDto) {
        return courseService.updateCourse(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
