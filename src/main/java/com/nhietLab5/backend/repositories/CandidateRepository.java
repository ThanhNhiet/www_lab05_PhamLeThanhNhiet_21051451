package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query("SELECT c FROM Candidate c " +
            "JOIN c.candidateSkills cs " +
            "JOIN JobSkill js ON cs.skill.id = js.skill.id " +
            "JOIN js.job j " +
            "JOIN j.company co " +
            "WHERE j.jobName = :jobName " +
            "AND co.id = :companyId " +
            "AND js.skillLevel = cs.skillLevel " +
            "GROUP BY c.id " +
            "HAVING COUNT(cs.skill.id) = (SELECT COUNT(jsInner.skill.id) " +
            "                             FROM JobSkill jsInner " +
            "                             JOIN jsInner.job jInner " +
            "                             WHERE jInner.jobName = :jobName " +
            "                             AND jInner.company.id = :companyId)")
    Page<Candidate> findSuitableCandidatesForJob(@Param("jobName") String jobName, @Param("companyId") Long companyId, Pageable pageable);

}
