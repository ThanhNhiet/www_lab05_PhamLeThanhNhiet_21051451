package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}