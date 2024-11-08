package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
//    @Query("SELECT c FROM Candidate c JOIN c.candidateSkills cs JOIN JobSkill js ON cs.skill.id = js.skill.id " +
//            "WHERE js.job.id = :jobId " +
//            "GROUP BY c.id " +
//            "HAVING COUNT(cs.skill.id) = (SELECT COUNT(jjs.skill.id) FROM JobSkill jjs WHERE jjs.job.id = :jobId)")
//    List<Candidate> findCandidatesSuitableForJob(@Param("jobId") Long jobId);
}
