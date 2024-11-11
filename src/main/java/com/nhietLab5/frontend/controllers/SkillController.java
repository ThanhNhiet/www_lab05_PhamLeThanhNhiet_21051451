package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.models.Company;
import com.nhietLab5.backend.models.Job;
import com.nhietLab5.backend.models.JobSkill;
import com.nhietLab5.backend.models.Skill;
import com.nhietLab5.backend.repositories.CompanyRepository;
import com.nhietLab5.backend.repositories.JobRepository;
import com.nhietLab5.backend.repositories.JobSkillRepository;
import com.nhietLab5.backend.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SkillController {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @GetMapping("/skills")
    @ResponseBody
    public List<String> getSkillNames() {
        List<Skill> skills = skillRepository.findAll();
        return skills.stream()
                .map(Skill::getSkillName)
                .collect(Collectors.toList());
    }

    @GetMapping("/skillsMissing")
    public String getSkillsMissing(Model model,
                                   @RequestParam("candidateId") Optional<Long> candidateId,
                                   @RequestParam("jobName") Optional<String> jobName,
                                   @RequestParam("companyName") Optional<String> companyName) {
        Long canId = candidateId.orElse(1L);
        String sJobName = jobName.orElse(null);
        String sCompanyName = companyName.orElse(null);

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

        return "skills/skillsMissing";
    }
}
