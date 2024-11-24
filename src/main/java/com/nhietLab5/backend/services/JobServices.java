package com.nhietLab5.backend.services;

import com.nhietLab5.backend.models.Job;
import com.nhietLab5.backend.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class JobServices {
    @Autowired
    private JobRepository jobRepository;

    public Page<Job> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return jobRepository.findAll(pageable);
    }

    public Page<Job> findByCompanyID(int pageNo, int pageSize, String sortBy, String sortDirection, Long id) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return jobRepository.findByCompanyId(id, pageable);
    }

    public Page<Job> findSuitableJobsForCandidate(int pageNo, int pageSize, String sortBy, String sortDirection, Long candidateId) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return jobRepository.findSuitableJobsForCandidate(candidateId, pageable);
    }

    public Page<Job> findJobsByJobName(int pageNo, int pageSize, String sortBy, String sortDirection, String jobName) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return jobRepository.findJobsByJobNameLike(jobName, pageable);
    }

    public Page<Job> findJobsByCompany_CompName(int pageNo, int pageSize, String sortBy, String sortDirection, String compName) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return jobRepository.findJobsByCompany_CompNameLike(compName, pageable);
    }
}
