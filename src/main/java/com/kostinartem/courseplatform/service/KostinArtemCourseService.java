package com.kostinartem.courseplatform.service;

import com.kostinartem.courseplatform.dto.KostinArtemCourseRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemCourseResponseDto;
import com.kostinartem.courseplatform.entity.KostinArtemCourse;
import com.kostinartem.courseplatform.entity.KostinArtemUser;
import com.kostinartem.courseplatform.exception.KostinArtemNotFoundException;
import com.kostinartem.courseplatform.repository.KostinArtemCourseRepository;
import com.kostinartem.courseplatform.repository.KostinArtemUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KostinArtemCourseService {

    private final KostinArtemCourseRepository courseRepository;
    private final KostinArtemUserRepository userRepository;

    public List<KostinArtemCourseResponseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public KostinArtemCourseResponseDto getCourseById(Long id) {
        KostinArtemCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new KostinArtemNotFoundException("Course not found with id: " + id));
        return mapToResponse(course);
    }

    public KostinArtemCourseResponseDto createCourse(KostinArtemCourseRequestDto requestDto) {
        KostinArtemUser teacher = userRepository.findById(requestDto.getTeacherId())
                .orElseThrow(() -> new KostinArtemNotFoundException("Teacher not found with id: " + requestDto.getTeacherId()));

        KostinArtemCourse course = new KostinArtemCourse();
        course.setTitle(requestDto.getTitle());
        course.setDescription(requestDto.getDescription());
        course.setCategory(requestDto.getCategory());
        course.setLevel(requestDto.getLevel());
        course.setPrice(requestDto.getPrice());
        course.setActive(requestDto.getActive());
        course.setTeacher(teacher);

        return mapToResponse(courseRepository.save(course));
    }

    public KostinArtemCourseResponseDto updateCourse(Long id, KostinArtemCourseRequestDto requestDto) {
        KostinArtemCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new KostinArtemNotFoundException("Course not found with id: " + id));

        KostinArtemUser teacher = userRepository.findById(requestDto.getTeacherId())
                .orElseThrow(() -> new KostinArtemNotFoundException("Teacher not found with id: " + requestDto.getTeacherId()));

        course.setTitle(requestDto.getTitle());
        course.setDescription(requestDto.getDescription());
        course.setCategory(requestDto.getCategory());
        course.setLevel(requestDto.getLevel());
        course.setPrice(requestDto.getPrice());
        course.setActive(requestDto.getActive());
        course.setTeacher(teacher);

        return mapToResponse(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        KostinArtemCourse course = courseRepository.findById(id)
                .orElseThrow(() -> new KostinArtemNotFoundException("Course not found with id: " + id));
        courseRepository.delete(course);
    }

    private KostinArtemCourseResponseDto mapToResponse(KostinArtemCourse course) {
        KostinArtemCourseResponseDto dto = new KostinArtemCourseResponseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setCategory(course.getCategory());
        dto.setLevel(course.getLevel());
        dto.setPrice(course.getPrice());
        dto.setActive(course.getActive());
        dto.setCreatedAt(course.getCreatedAt());
        dto.setTeacherId(course.getTeacher().getId());
        dto.setTeacherName(course.getTeacher().getFullName());
        return dto;
    }
}
