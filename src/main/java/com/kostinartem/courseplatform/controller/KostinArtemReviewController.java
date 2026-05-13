package com.kostinartem.courseplatform.controller;

import com.kostinartem.courseplatform.dto.KostinArtemReviewRequestDto;
import com.kostinartem.courseplatform.dto.KostinArtemReviewResponseDto;
import com.kostinartem.courseplatform.service.KostinArtemReviewService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KostinArtemReviewController {

    private final KostinArtemReviewService reviewService;

    @PostMapping("/reviews")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<KostinArtemReviewResponseDto> createReview(
            @Valid @RequestBody KostinArtemReviewRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(requestDto));
    }

    @GetMapping("/courses/{courseId}/reviews")
    @PreAuthorize("hasRole('STUDENT')")
    public List<KostinArtemReviewResponseDto> getCourseReviews(@PathVariable Long courseId,
                                                               @RequestParam(required = false) Integer rating) {
        return reviewService.getCourseReviews(courseId, rating);
    }
}
