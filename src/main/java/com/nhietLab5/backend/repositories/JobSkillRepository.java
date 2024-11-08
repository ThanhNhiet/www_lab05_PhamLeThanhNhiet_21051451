package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.Job;
import com.nhietLab5.backend.models.JobSkill;
import com.nhietLab5.backend.models.JobSkillId;
import com.nhietLab5.backend.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
    List<JobSkill> findAllByJobId(Long jobId);

    JobSkill findByJobAndSkill(Job job, Skill skill);
}