package com.kostinartem.courseplatform.controller;

import com.kostinartem.courseplatform.dto.KostinArtemLessonRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemLessonResponseDto;
import com.kostinartem.courseplatform.service.KostinArtemLessonService;
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
@RequiredArgsConstructor
public class KostinArtemLessonController {

    private final KostinArtemLessonService lessonService;

    @GetMapping("/api/lessons")
    public List<KostinArtemLessonResponseDto> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/api/lessons/{id}")
    public KostinArtemLessonResponseDto getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @GetMapping("/api/courses/{courseId}/lessons")
    public List<KostinArtemLessonResponseDto> getLessonsByCourseId(@PathVariable Long courseId) {
        return lessonService.getLessonsByCourseId(courseId);
    }

    @PostMapping("/api/lessons")
    public ResponseEntity<KostinArtemLessonResponseDto> createLesson(
            @Valid @RequestBody KostinArtemLessonRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.createLesson(requestDto));
    }

    @PutMapping("/api/lessons/{id}")
    public KostinArtemLessonResponseDto updateLesson(@PathVariable Long id,
                                                     @Valid @RequestBody KostinArtemLessonRequestDto requestDto) {
        return lessonService.updateLesson(id, requestDto);
    }

    @DeleteMapping("/api/lessons/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
