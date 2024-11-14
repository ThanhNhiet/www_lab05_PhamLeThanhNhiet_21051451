package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.Candidate;
import com.nhietLab5.backend.models.CandidateSkill;
import com.nhietLab5.backend.models.CandidateSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
    List<CandidateSkill> findByCan(Candidate can);
}