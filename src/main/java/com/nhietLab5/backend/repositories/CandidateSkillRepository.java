package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.CandidateSkill;
import com.nhietLab5.backend.models.CandidateSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
}