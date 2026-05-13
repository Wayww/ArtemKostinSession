package com.kostinartem.courseplatform.service;

import com.kostinartem.courseplatform.dto.KostinArtemLessonRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemLessonResponseDto;
import com.kostinartem.courseplatform.entity.KostinArtemCourse;
import com.kostinartem.courseplatform.entity.KostinArtemLesson;
import com.kostinartem.courseplatform.exception.KostinArtemNotFoundException;
import com.kostinartem.courseplatform.repository.KostinArtemCourseRepository;
import com.kostinartem.courseplatform.repository.KostinArtemLessonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KostinArtemLessonService {

    private final KostinArtemLessonRepository lessonRepository;
    private final KostinArtemCourseRepository courseRepository;

    public List<KostinArtemLessonResponseDto> getAllLessons() {
        return lessonRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<KostinArtemLessonResponseDto> getLessonsByCourseId(Long courseId) {
        return lessonRepository.findByCourseId(courseId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public KostinArtemLessonResponseDto getLessonById(Long id) {
        KostinArtemLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new KostinArtemNotFoundException("Lesson not found with id: " + id));
        return mapToResponse(lesson);
    }

    public KostinArtemLessonResponseDto createLesson(KostinArtemLessonRequestDto requestDto) {
        KostinArtemCourse course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new KostinArtemNotFoundException("Course not found with id: " + requestDto.getCourseId()));

        KostinArtemLesson lesson = new KostinArtemLesson();
        lesson.setTitle(requestDto.getTitle());
        lesson.setContent(requestDto.getContent());
        lesson.setVideoUrl(requestDto.getVideoUrl());
        lesson.setLessonOrder(requestDto.getLessonOrder());
        lesson.setCourse(course);

        return mapToResponse(lessonRepository.save(lesson));
    }

    public KostinArtemLessonResponseDto updateLesson(Long id, KostinArtemLessonRequestDto requestDto) {
        KostinArtemLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new KostinArtemNotFoundException("Lesson not found with id: " + id));

        KostinArtemCourse course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new KostinArtemNotFoundException("Course not found with id: " + requestDto.getCourseId()));

        lesson.setTitle(requestDto.getTitle());
        lesson.setContent(requestDto.getContent());
        lesson.setVideoUrl(requestDto.getVideoUrl());
        lesson.setLessonOrder(requestDto.getLessonOrder());
        lesson.setCourse(course);

        return mapToResponse(lessonRepository.save(lesson));
    }

    public void deleteLesson(Long id) {
        KostinArtemLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new KostinArtemNotFoundException("Lesson not found with id: " + id));
        lessonRepository.delete(lesson);
    }

    private KostinArtemLessonResponseDto mapToResponse(KostinArtemLesson lesson) {
        KostinArtemLessonResponseDto dto = new KostinArtemLessonResponseDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setContent(lesson.getContent());
        dto.setVideoUrl(lesson.getVideoUrl());
        dto.setLessonOrder(lesson.getLessonOrder());
        dto.setCreatedAt(lesson.getCreatedAt());
        dto.setCourseId(lesson.getCourse().getId());
        return dto;
    }
}
