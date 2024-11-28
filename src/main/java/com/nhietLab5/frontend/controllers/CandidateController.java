package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.enums.SkillLevel;
import com.nhietLab5.backend.models.*;
import com.nhietLab5.backend.repositories.*;
import com.nhietLab5.backend.services.JobServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private JobServices jobServices;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;
    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("/jobList")
    public String showJobListPaging(Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("candidateId") Optional<Long> candidateId) {
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

        Long id = candidateId.orElse(null);
        model.addAttribute("candidate", candidateRepository.findById(id).orElse(null));
        return "candidates/jobs";
    }

    @GetMapping("/jobDetail")
    public String showJobDetail(Model model,
                                @RequestParam("jobId") String id,
                                @RequestParam("candidateId") Optional<Long> candidateId) {
        Job job = jobRepository.findById(Long.parseLong(id)).orElse(null);
        Long candidateID = candidateId.orElse(null);
        List<JobSkill> jobSkill = jobSkillRepository.findAllByJobId(Long.parseLong(id));
        job.setJobSkills(jobSkill);
        model.addAttribute("job", job);

        model.addAttribute("candidate", candidateRepository.findById(candidateID).orElse(null));
        return "candidates/jobDetails";

    }

    @GetMapping("/detail")
    public String showCandidateDetail(Model model,
                                      @RequestParam("canID") Long canID){
        Candidate candidate = candidateRepository.findById(canID).orElse(null);
        model.addAttribute("candidate", candidate);

        List<Experience> experiences = experienceRepository.findByCandidate(candidate);
        model.addAttribute("experiences", experiences);

        List<CandidateSkill> candidateSkills = candidateSkillRepository.findByCan(candidate);
        model.addAttribute("candidateSkills", candidateSkills);

        return "candidates/candidateDetail";
    }

    @GetMapping("/suitableJobs")
    public String showSuitableJobs(Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   @RequestParam("candidateId") String candidateId) {
        Long id = Long.parseLong(candidateId);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobServices.findSuitableJobsForCandidate(
                currentPage - 1, pageSize, "id", "asc", id);
        model.addAttribute("jobPage", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("candidate", candidateRepository.findById(id).orElse(null));
        return "candidates/jobsSuitable";
    }

    @GetMapping("/searchJobs")
    public String searchJob(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            @RequestParam("candidateId") String candidateId,
                            @RequestParam("condition") String condition,
                            @RequestParam("inputValue") String input) {
        Long id = Long.parseLong(candidateId);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        if (condition.equals("jobName")) {
            Page<Job> jobPage = jobServices.findJobsByJobName(
                    currentPage - 1, pageSize, "id", "asc", input);
            model.addAttribute("jobPage", jobPage);
            int totalPages = jobPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
        } else if (condition.equals("company")) {
            Page<Job> jobPage = jobServices.findJobsByCompany_CompName(
                    currentPage - 1, pageSize, "id", "asc", input);
            model.addAttribute("jobPage", jobPage);
            int totalPages = jobPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
        }
        model.addAttribute("candidate", candidateRepository.findById(id).orElse(null));
        return "candidates/jobs";
    }

    @GetMapping("/missingSkills")
    public String getSkillsMissing(Model model,
                                   @RequestParam("candidateId") Optional<Long> candidateId,
                                   @RequestParam("jobName") Optional<String> jobName,
                                   @RequestParam("companyName") Optional<String> companyName) {
        Long canId = candidateId.orElse(null);
        String sJobName = jobName.orElse(null);
        String sCompanyName = companyName.orElse(null);

        model.addAttribute("candidate", candidateRepository.findById(canId).orElse(null));

        List<Skill> skills = skillRepository.findMissingSkillsForCandidateByJobAndCompany(sJobName, sCompanyName, canId);
        model.addAttribute("skillsMissing", skills);

        Job job = jobRepository.findByJobNameAndCompany_CompName(sJobName, sCompanyName);
        model.addAttribute("job", job);

        Map<Long, JobSkill> skillJobSkillMapById = new HashMap<>();
        for (Skill skill : skills) {
            JobSkill jobSkill = jobSkillRepository.findByJobAndSkill(job, skill);
            if (jobSkill != null) {
                skillJobSkillMapById.put(skill.getId(), jobSkill);
            }
        }
        model.addAttribute("skillJobSkillMapById", skillJobSkillMapById);

        return "candidates/skillsMissing";
    }

    @GetMapping("/editInfo")
    public String editInfo(Model model,
                           @RequestParam("canID") Long canID){
        Candidate candidate = candidateRepository.findById(canID).orElse(null);
        List<Experience> experiences = experienceRepository.findByCandidate(candidate);

        model.addAttribute("candidate", candidate);
        model.addAttribute("experiences", experiences);

        return "candidates/editInfo_candidate";
    }

    @PostMapping("/editInfo/saveChange")
    public String saveCandidateUser(@RequestParam Long id,
                                    @RequestParam String fullName,
                                    @RequestParam String dob,
                                    @RequestParam String phone,
                                    @RequestParam String city,
                                    @RequestParam String zip,
                                    @RequestParam String street,
                                    @RequestParam String number,
                                    @RequestParam String exId,
                                    @RequestParam String companyName,
                                    @RequestParam String role,
                                    @RequestParam String startDate,
                                    @RequestParam String endDate,
                                    @RequestParam String workDescription) {

        Candidate candidate = candidateRepository.findById(id).orElse(null);

        LocalDate birthDate = LocalDate.parse(dob);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        candidate.setFullName(fullName);
        candidate.setDob(birthDate);
        candidate.setPhone(phone);
        candidate.getAddress().setCity(city);
        candidate.getAddress().setZipcode(zip);
        candidate.getAddress().setStreet(street);
        candidate.getAddress().setNumber(number);
        candidateRepository.save(candidate);

        Experience experience = experienceRepository.findById(Long.parseLong(exId)).orElse(null);
        experience.setCompanyName(companyName);
        experience.setRole(role);
        experience.setFromDate(start);
        experience.setToDate(end);
        experience.setWorkDescription(workDescription);
        experienceRepository.save(experience);

        return "redirect:/candidate/detail?canID=" + id;
    }

    @PostMapping("detail/addSkill")
    public String addSkill(@RequestParam("canId") Long canID,
                           @RequestParam("skillInput") String skillName,
                           @RequestParam("levelInput") String level,
                           @RequestParam("moreInfosInput") String moreInfos){
        try {
            Skill skill = skillRepository.findBySkillName(skillName);
            Candidate candidate = candidateRepository.findById(canID).orElse(null);

            CandidateSkillId candidateSkillId = new CandidateSkillId();
            candidateSkillId.setCanId(canID);
            candidateSkillId.setSkillId(skill.getId());

            CandidateSkill candidateSkill = new CandidateSkill();
            candidateSkill.setId(candidateSkillId);
            candidateSkill.setCan(candidate);
            candidateSkill.setSkill(skill);
            candidateSkill.setSkillLevel(SkillLevel.valueOf(level.toUpperCase()));
            candidateSkill.setMoreInfos(moreInfos);
            candidateSkillRepository.save(candidateSkill);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/candidate/detail?canID=" + canID;
        }
        return "redirect:/candidate/detail?canID=" + canID;
    }

    @PostMapping("/detail/editSkill")
    public String editSkill(@RequestParam("canId") Long canID,
                            @RequestParam("skillId") Long skillID,
                            @RequestParam("levelInput") String level,
                            @RequestParam("moreInfosInput") String moreInfos){
        Candidate candidate = candidateRepository.findById(canID).orElse(null);
        Skill skill = skillRepository.findById(skillID).orElse(null);
        CandidateSkill candidateSkill = candidateSkillRepository.findByCanAndSkill(candidate, skill);
        candidateSkill.setSkillLevel(SkillLevel.valueOf(level.toUpperCase()));
        candidateSkill.setMoreInfos(moreInfos);
        candidateSkillRepository.save(candidateSkill);
        return "redirect:/candidate/detail?canID=" + canID;
    }

    @GetMapping("/detail/deleteSkill")
    public String deleteSkill(@RequestParam Long canID,
                              @RequestParam Long skillID){
        Candidate candidate = candidateRepository.findById(canID).orElse(null);
        Skill skill = skillRepository.findById(skillID).orElse(null);
        CandidateSkill candidateSkill = candidateSkillRepository.findByCanAndSkill(candidate, skill);
        candidateSkillRepository.delete(candidateSkill);
        return "redirect:/candidate/detail?canID=" + canID;
    }


}
