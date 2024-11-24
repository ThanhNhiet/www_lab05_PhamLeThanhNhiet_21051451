package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.Company;
import com.nhietLab5.backend.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByCompanyId(Long compId, Pageable pageable);

    Job findByJobNameAndCompany_CompName(String jobName, String company);

    @Query("SELECT j FROM Job j " +
            "JOIN j.jobSkills js " +
            "JOIN CandidateSkill cs ON js.skill.id = cs.skill.id " +
            "WHERE cs.can.id = :candidateId " +
            "AND cs.skillLevel >= js.skillLevel " +
            "GROUP BY j.id " +
            "HAVING COUNT(js.skill.id) = (SELECT COUNT(jsInner.skill.id) " +
            "                             FROM JobSkill jsInner " +
            "                             WHERE jsInner.job.id = j.id)")
//    List<Job>findSuitableJobsForCandidate(@Param("candidateId") Long candidateId);
    Page<Job>findSuitableJobsForCandidate(@Param("candidateId") Long candidateId, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.jobName LIKE %:jobName%")
    Page<Job> findJobsByJobNameLike(@Param("jobName") String jobName, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.company.compName LIKE %:compName%")
    Page<Job> findJobsByCompany_CompNameLike(@Param("compName") String compName, Pageable pageable);
}