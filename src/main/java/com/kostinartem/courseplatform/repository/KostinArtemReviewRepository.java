package com.kostinartem.courseplatform.repository;

import com.kostinartem.courseplatform.entity.KostinArtemReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KostinArtemReviewRepository extends JpaRepository<KostinArtemReview, Long> {

    List<KostinArtemReview> findByCourseId(Long courseId);

    List<KostinArtemReview> findByCourseIdAndRating(Long courseId, Integer rating);
}
