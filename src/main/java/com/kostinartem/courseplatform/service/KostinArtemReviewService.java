package com.kostinartem.courseplatform.service;

import com.kostinartem.courseplatform.dto.KostinArtemReviewRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemReviewResponseDto;
import com.kostinartem.courseplatform.entity.KostinArtemCourse;
import com.kostinartem.courseplatform.entity.KostinArtemReview;
import com.kostinartem.courseplatform.entity.KostinArtemUser;
import com.kostinartem.courseplatform.exception.KostinArtemNotFoundException;
import com.kostinartem.courseplatform.repository.KostinArtemCourseRepository;
import com.kostinartem.courseplatform.repository.KostinArtemReviewRepository;
import com.kostinartem.courseplatform.repository.KostinArtemUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KostinArtemReviewService {

    private final KostinArtemReviewRepository reviewRepository;
    private final KostinArtemUserRepository userRepository;
    private final KostinArtemCourseRepository courseRepository;

    public KostinArtemReviewResponseDto createReview(KostinArtemReviewRequestDto requestDto) {
        KostinArtemUser user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new KostinArtemNotFoundException("User not found with id: " + requestDto.getUserId()));

        KostinArtemCourse course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new KostinArtemNotFoundException("Course not found with id: " + requestDto.getCourseId()));

        KostinArtemReview review = new KostinArtemReview();
        review.setRating(requestDto.getRating());
        review.setComment(requestDto.getComment());
        review.setUser(user);
        review.setCourse(course);

        return mapToResponse(reviewRepository.save(review));
    }

    public List<KostinArtemReviewResponseDto> getCourseReviews(Long courseId, Integer rating) {
        List<KostinArtemReview> reviews = rating == null
                ? reviewRepository.findByCourseId(courseId)
                : reviewRepository.findByCourseIdAndRating(courseId, rating);

        return reviews.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private KostinArtemReviewResponseDto mapToResponse(KostinArtemReview review) {
        KostinArtemReviewResponseDto dto = new KostinArtemReviewResponseDto();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUserId(review.getUser().getId());
        dto.setCourseId(review.getCourse().getId());
        return dto;
    }
}
