package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByCompanyId(Long compId, Pageable pageable);
}