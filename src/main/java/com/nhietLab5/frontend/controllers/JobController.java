package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.models.Job;
import com.nhietLab5.backend.models.JobSkill;
import com.nhietLab5.backend.repositories.*;
import com.nhietLab5.backend.services.JobServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobServices jobServices;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/myPost")
    public String showCompanyPosts(Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("id") String id) {
        Long companyID = Long.parseLong(id);
        model.addAttribute("company", companyRepository.findById(companyID).orElse(null));
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobServices.findByCompanyID(
                currentPage - 1, pageSize, "id", "asc", companyID);
        model.addAttribute("jobPage", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "companies/companyJobs";
    }

    @PostMapping("/add")
    public String addJob(Model model,
                         @RequestParam("companyId") String id,
                         @RequestParam("jobName") String jobName,
                         @RequestParam("jobDesc") String jobDesc) {
        Long companyId = Long.parseLong(id);
        Job job = new Job();
        job.setJobName(jobName);
        job.setCompany(companyRepository.findById(companyId).orElse(null));
        job.setJobDesc(jobDesc);
        jobRepository.save(job);
        return "redirect:/jobs/myPost?id=" + companyId;
    }

    @PostMapping("/edit")
    public String editJob(Model model,
                          @RequestParam("jobId") String jobId,
                          @RequestParam("companyId") String id,
                          @RequestParam("jobName") String jobName,
                          @RequestParam("jobDesc") String jobDesc) {
        Long companyId = Long.parseLong(id);
        jobRepository.findById(Long.parseLong(jobId)).ifPresent(job -> {
            job.setJobName(jobName);
            job.setJobDesc(jobDesc);
            jobRepository.save(job);
        });
        return "redirect:/jobs/myPost?id=" + companyId;
    }

    @GetMapping("/delete")
    public String deleteJob(@RequestParam("jobId") Long jobId, @RequestParam("companyId") Long companyId) {
        List<JobSkill> jobSkills = jobSkillRepository.findAllByJobId(jobId);
        if(jobSkills != null) {
            for (JobSkill jobSkill : jobSkills) {
                jobSkillRepository.delete(jobSkill);
            }
        }
        jobRepository.findById(jobId).ifPresent(job -> {
            jobRepository.delete(job);
        });
        return "redirect:/jobs/myPost?id=" + companyId;
    }


}
