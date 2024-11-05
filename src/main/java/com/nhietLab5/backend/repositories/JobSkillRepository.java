package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.JobSkill;
import com.nhietLab5.backend.models.JobSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
}