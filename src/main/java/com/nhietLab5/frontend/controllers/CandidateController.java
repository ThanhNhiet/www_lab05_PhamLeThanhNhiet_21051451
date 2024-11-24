package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.models.Candidate;
import com.nhietLab5.backend.models.CandidateSkill;
import com.nhietLab5.backend.models.Company;
import com.nhietLab5.backend.models.Experience;
import com.nhietLab5.backend.repositories.CandidateRepository;
import com.nhietLab5.backend.repositories.CandidateSkillRepository;
import com.nhietLab5.backend.repositories.CompanyRepository;
import com.nhietLab5.backend.repositories.ExperienceRepository;
import com.nhietLab5.backend.services.CandidateServices;
import com.nhietLab5.backend.services.EmailSenderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/candidates")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateServices candidateServices;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private EmailSenderServices emailSenderServices;

    @GetMapping("/list")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size,
                                          @RequestParam("companyId") Optional<Long> companyId) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> candidatePage= candidateServices.findAll(
                currentPage - 1,pageSize,"id","asc");
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
        return "companies/candidates-paging";
    }

    @GetMapping("/suitable")
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
                            @RequestParam("canEmail") String canEmail) {
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
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/candidates/list?companyId=" + company.getId();
        }


        return "redirect:/candidates/list?companyId=" + company.getId();
    }

    @GetMapping("/detail")
    public String showCandidateDetail(Model model,
                                      @RequestParam("canID") Long canID,
                                      @RequestParam(value = "compID", required = false) Long compID) {
        Candidate candidate = candidateRepository.findById(canID).orElse(null);
        model.addAttribute("candidate", candidate);

        Experience experience = experienceRepository.findByCandidate(candidate);
        model.addAttribute("experience", experience);

        List<CandidateSkill> candidateSkills = candidateSkillRepository.findByCan(candidate);
        model.addAttribute("candidateSkills", candidateSkills);

        if(compID != null){
            model.addAttribute("company", companyRepository.findById(compID).orElse(null));
            return "companies/candidateDetail";
        }

        return "candidates/candidateDetail";
    }
}
