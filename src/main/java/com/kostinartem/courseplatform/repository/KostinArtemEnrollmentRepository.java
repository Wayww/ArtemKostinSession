package com.kostinartem.courseplatform.repository;

import com.kostinartem.courseplatform.entity.KostinArtemEnrollment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KostinArtemEnrollmentRepository extends JpaRepository<KostinArtemEnrollment, Long> {

    List<KostinArtemEnrollment> findByUserId(Long userId);
}
