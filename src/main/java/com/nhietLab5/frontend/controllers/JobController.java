package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.enums.SkillLevel;
import com.nhietLab5.backend.models.Job;
import com.nhietLab5.backend.models.JobSkill;
import com.nhietLab5.backend.models.JobSkillId;
import com.nhietLab5.backend.models.Skill;
import com.nhietLab5.backend.repositories.CompanyRepository;
import com.nhietLab5.backend.repositories.JobRepository;
import com.nhietLab5.backend.repositories.JobSkillRepository;
import com.nhietLab5.backend.repositories.SkillRepository;
import com.nhietLab5.backend.services.JobServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
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

    @GetMapping("/jobs")
    public String showJobListPaging(Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobServices.findAll(
                currentPage - 1, pageSize, "id", "asc");
        model.addAttribute("jobPage", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "jobs/jobs";
    }

    @GetMapping("/myPost")
    public String showCompanyPosts(Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("id") String id) {
        Long companyID = Long.parseLong(id);
        model.addAttribute("companyId", id);
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
        return "jobs/companyJobs";
    }

    @PostMapping("/addJob")
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
        return "redirect:/myPost?id=" + companyId;
    }

    @PostMapping("/editJob")
    public String editJob(Model model,
                          @RequestParam("jobId") String jobId,
                          @RequestParam("companyId") String id,
                          @RequestParam("jobName") String jobName,
                          @RequestParam("jobDesc") String jobDesc) {
        Long companyId = Long.parseLong(id);
        // Find the job
        jobRepository.findById(Long.parseLong(jobId)).ifPresent(job -> {
            // Update the job
            job.setJobName(jobName);
            job.setJobDesc(jobDesc);
            jobRepository.save(job);
        });
        return "redirect:/myPost?id=" + companyId;
    }

    @GetMapping("/deleteJob")
    public String deleteJob(@RequestParam("jobId") Long jobId, @RequestParam("companyId") Long companyId) {
        List<JobSkill> jobSkills = jobSkillRepository.findAllByJobId(jobId);
        if(jobSkills != null) {
            for (JobSkill jobSkill : jobSkills) {
                jobSkillRepository.delete(jobSkill);
            }
        }
        // Find and delete the job
        jobRepository.findById(jobId).ifPresent(job -> {
            jobRepository.delete(job);
        });
        // Redirect to the list of jobs for the company
        return "redirect:/myPost?id=" + companyId;
    }

    @GetMapping("/jobDetail")
    public String showJobDetail(Model model,
                                @RequestParam("jobId") String id,
                                @RequestParam("companyId") String companyId) {
        Job job = jobRepository.findById(Long.parseLong(id)).orElse(null);
        List<JobSkill> jobSkill = jobSkillRepository.findAllByJobId(Long.parseLong(id));
        job.setJobSkills(jobSkill);
        model.addAttribute("job", job);
        model.addAttribute("companyId", companyId);
        return "jobs/jobDetails";
    }

}
