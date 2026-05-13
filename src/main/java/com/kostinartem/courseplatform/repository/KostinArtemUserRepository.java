package com.kostinartem.courseplatform.repository;

import com.kostinartem.courseplatform.entity.KostinArtemUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KostinArtemUserRepository extends JpaRepository<KostinArtemUser, Long> {

    Optional<KostinArtemUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
