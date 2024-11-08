package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findBySkillName(String skillName);

    @Query("SELECT js.skill " +
            "FROM JobSkill js " +
            "JOIN js.job j " +
            "JOIN j.company co " +
            "WHERE j.jobName = :jobName " +
            "AND co.compName = :companyName " +
            "AND js.skill.id NOT IN (" +
            "    SELECT cs.skill.id " +
            "    FROM CandidateSkill cs " +
            "    WHERE cs.can.id = :candidateId " +
            "    AND cs.skillLevel >= js.skillLevel" +
            ")")
    List<Skill> findMissingSkillsForCandidateByJobAndCompany(
            @Param("jobName") String jobName,
            @Param("companyName") String companyName,
            @Param("candidateId") Long candidateId);

}