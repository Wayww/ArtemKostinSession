package com.kostinartem.courseplatform.repository;

import com.kostinartem.courseplatform.entity.KostinArtemLesson;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KostinArtemLessonRepository extends JpaRepository<KostinArtemLesson, Long> {

    List<KostinArtemLesson> findByCourseId(Long courseId);
}
