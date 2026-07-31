package com.cohortteam6.repository;

import com.cohortteam6.repository.entity.ReconBreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReconBreakRepository extends JpaRepository<ReconBreak, Long> {
    long countByStatus(String status);
}
