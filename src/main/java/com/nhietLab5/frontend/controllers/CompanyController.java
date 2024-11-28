package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.models.*;
import com.nhietLab5.backend.repositories.*;
import com.nhietLab5.backend.services.CandidateServices;
import com.nhietLab5.backend.services.EmailSenderServices;
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
@RequestMapping("/company")
public class CompanyController {
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
    @Autowired
    private CandidateServices candidateServices;
    @Autowired
    private EmailSenderServices emailSenderServices;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @GetMapping("/candidateList")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size,
                                          @RequestParam("companyId") Optional<Long> companyId) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> candidatePage = candidateServices.findAll(
                currentPage - 1, pageSize, "id", "asc");
        model.addAttribute("candidatePage", candidatePage);
        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        Long id = companyId.orElse(null);
        model.addAttribute("company", companyRepository.findById(id).orElse(null));
        return "companies/candidates";
    }

    @GetMapping("/suitableCandidates")
    public String showSuitableCandidatesForJob(Model model,
                                               @RequestParam("jobName") String jobName,
                                               @RequestParam("companyId") Long companyId,
                                               @RequestParam("page") Optional<Integer> page,
                                               @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> candidatePage = candidateServices.findSuitableCandidatesForJob(
                jobName, companyId, currentPage - 1, pageSize, "id", "asc");
        model.addAttribute("candidatePage", candidatePage);
        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("jobName", jobName);
        model.addAttribute("company", companyRepository.findById(companyId).orElse(null));
        return "companies/candidatesSuitable";
    }

    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam("compEmail") String compEmail,
                            @RequestParam("canEmail") String canEmail,
                            @RequestParam("companyId") Long companyId,
                            @RequestParam("jobName") String jobName,
                            Model model) {
        Candidate candidate = candidateRepository.findByEmail(canEmail);
        Company company = companyRepository.findByEmail(compEmail);
        try {
            emailSenderServices.sendEmail(
                    compEmail,
                    canEmail,
                    "Job Offer",
                    "Dear " + candidate.getFullName() + ",\n" +
                            "We are pleased to offer you a job at " + company.getCompName() + ".\n" +
                            "Please contact us for more information.\n" +
                            "Best regards,\n" +
                            company.getCompName() + "."
            );
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Sending email failed!");
//            return "redirect:/company/candidateList?companyId=" + company.getId();
            return "redirect:/company/suitableCandidates?companyId=" + companyId + "&jobName=" + jobName;
        }
        return "redirect:/company/candidateList?companyId=" + company.getId();
    }

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
        return "redirect:/company/myPost?id=" + companyId;
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
        return "redirect:/company/myPost?id=" + companyId;
    }

    @GetMapping("myPost/delete")
    public String deleteJob(@RequestParam("jobId") Long jobId, @RequestParam("companyId") Long companyId) {
        List<JobSkill> jobSkills = jobSkillRepository.findAllByJobId(jobId);
        if (jobSkills != null) {
            for (JobSkill jobSkill : jobSkills) {
                jobSkillRepository.delete(jobSkill);
            }
        }
        jobRepository.findById(jobId).ifPresent(job -> {
            jobRepository.delete(job);
        });
        return "redirect:/company/myPost?id=" + companyId;
    }

    @GetMapping("/jobDetail")
    public String showJobDetail(Model model,
                                @RequestParam("jobId") String id,
                                @RequestParam("companyId") String companyId) {
        Job job = jobRepository.findById(Long.parseLong(id)).orElse(null);
        List<JobSkill> jobSkill = jobSkillRepository.findAllByJobId(Long.parseLong(id));
        job.setJobSkills(jobSkill);
        model.addAttribute("job", job);
        model.addAttribute("company", companyRepository.findById(Long.parseLong(companyId)).orElse(null));
        return "companies/jobDetails";
    }

    @GetMapping("/candidateDetail")
    public String showCandidateDetail(Model model,
                                      @RequestParam("canID") Long canID,
                                      @RequestParam("compID") Long compID) {
        Candidate candidate = candidateRepository.findById(canID).orElse(null);
        model.addAttribute("candidate", candidate);

        List<Experience> experiences = experienceRepository.findByCandidate(candidate);
        model.addAttribute("experiences", experiences);

        List<CandidateSkill> candidateSkills = candidateSkillRepository.findByCan(candidate);
        model.addAttribute("candidateSkills", candidateSkills);

        model.addAttribute("company", companyRepository.findById(compID).orElse(null));
        return "companies/candidateDetail";
    }

    @GetMapping("/myPost/searchMyJobs")
    public String searchMyJobs(Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   @RequestParam("companyId") String id,
                                   @RequestParam("inputValue") String inputValue) {
        Long companyID = Long.parseLong(id);
        Company company = companyRepository.findById(companyID).orElse(null);
        model.addAttribute("company", company);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobServices.findJobsByJobNameAndCompany(
                currentPage - 1, pageSize, "id", "asc", inputValue, companyID);
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

    @GetMapping("/editInfo")
    public String editInfo(Model model,
                           @RequestParam("id") Long companyId) {
        model.addAttribute("company", companyRepository.findById(companyId).orElse(null));
        return "companies/editInfo_Company";
    }

    @PostMapping("/editInfo/saveChange")
    public String saveChange(@RequestParam("id") Long companyId,
                             @RequestParam("compName") String compName,
                             @RequestParam("webUrl") String webUrl,
                             @RequestParam("phone") String phone,
                             @RequestParam("about") String about,
                             @RequestParam("city") String city,
                             @RequestParam("zip") String zip,
                             @RequestParam("street") String street,
                             @RequestParam("number") String number) {
        Company company = companyRepository.findById(companyId).orElse(null);
        company.setCompName(compName);
        company.setWebUrl(webUrl);
        company.setPhone(phone);
        company.setAbout(about);

        Address address = company.getAddress();
        address.setCity(city);
        address.setZipcode(zip);
        address.setStreet(street);
        address.setNumber(number);
        company.setAddress(address);

        companyRepository.save(company);
        return "redirect:/company/myPost?id=" + companyId;
    }
}
