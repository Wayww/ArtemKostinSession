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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class KostinArtemCourseService {

    private final KostinArtemCourseRepository courseRepository;
    private final KostinArtemUserRepository userRepository;

    public Page<KostinArtemCourseResponseDto> getAllCourses(int page,
                                                            int size,
                                                            String sortBy,
                                                            String direction,
                                                            String keyword,
                                                            String category,
                                                            String level) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<KostinArtemCourse> specification = (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

            if (StringUtils.hasText(keyword)) {
                String keywordPattern = "%" + keyword.toLowerCase() + "%";
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), keywordPattern),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), keywordPattern)
                        )
                );
            }

            if (StringUtils.hasText(category)) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.equal(criteriaBuilder.lower(root.get("category")), category.toLowerCase())
                );
            }

            if (StringUtils.hasText(level)) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.equal(criteriaBuilder.lower(root.get("level")), level.toLowerCase())
                );
            }

            return predicate;
        };

        return courseRepository.findAll(specification, pageable).map(this::mapToResponse);
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
