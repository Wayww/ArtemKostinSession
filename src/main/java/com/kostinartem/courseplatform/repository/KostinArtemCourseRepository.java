package com.kostinartem.courseplatform.repository;

import com.kostinartem.courseplatform.entity.KostinArtemCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KostinArtemCourseRepository extends JpaRepository<KostinArtemCourse, Long>,
        JpaSpecificationExecutor<KostinArtemCourse> {
}
