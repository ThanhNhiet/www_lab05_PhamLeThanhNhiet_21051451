package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.enums.SkillLevel;
import com.nhietLab5.backend.models.JobSkill;
import com.nhietLab5.backend.models.JobSkillId;
import com.nhietLab5.backend.repositories.JobRepository;
import com.nhietLab5.backend.repositories.JobSkillRepository;
import com.nhietLab5.backend.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JobSkillController {
    @Autowired
    private JobSkillRepository jobSkillRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private SkillRepository skillRepository;

    @PostMapping("/addJobSkill")
    public String addJobSkill(Model model,
                              @RequestParam("companyId") String companyId,
                              @RequestParam("jobId") String jobId,
                              @RequestParam("skillName") String skillName,
                              @RequestParam("moreInfo") String moreInfo,
                              @RequestParam("skillLevel") String skillLevel){
        try {
            JobSkillId jobSkillId = new JobSkillId();
            jobSkillId.setJobId(Long.parseLong(jobId));
            jobSkillId.setSkillId(skillRepository.findBySkillName(skillName).getId());

            JobSkill jobSkill = new JobSkill();
            jobSkill.setId(jobSkillId);
            jobSkill.setJob(jobRepository.findById(Long.parseLong(jobId)).get());
            jobSkill.setSkill(skillRepository.findBySkillName(skillName));
            jobSkill.setMoreInfos(moreInfo);
            jobSkill.setSkillLevel(SkillLevel.valueOf(skillLevel));
            jobSkillRepository.save(jobSkill);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/jobDetail?jobId=" + jobId + "&companyId=" + companyId;
        }
        return "redirect:/jobDetail?jobId=" + jobId + "&companyId=" + companyId;
    }

    @PostMapping("/editJobSkill")
    public String editJobSkill(Model model,
                               @RequestParam("companyId") String companyId,
                               @RequestParam("jobId") String jobId,
                               @RequestParam("skillId") String skillId,
                               @RequestParam("moreInfo") String moreInfo,
                               @RequestParam("skillLevel") String skillLevel){
        try {
            JobSkillId jobSkillId = new JobSkillId();
            jobSkillId.setJobId(jobRepository.findById(Long.parseLong(jobId)).get().getId());
            jobSkillId.setSkillId(skillRepository.findById(Long.parseLong(skillId)).get().getId());
            JobSkill jobSkill = jobSkillRepository.findById(jobSkillId).get();
            jobSkill.setMoreInfos(moreInfo);
            jobSkill.setSkillLevel(SkillLevel.valueOf(skillLevel));
            jobSkillRepository.save(jobSkill);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/jobDetail?jobId=" + jobId + "&companyId=" + companyId;
        }
        return "redirect:/jobDetail?jobId=" + jobId + "&companyId=" + companyId;
    }

    @GetMapping("/deleteJobSkill")
    public String deleteJobSkill(Model model,
                                 @RequestParam("companyId") String companyId,
                                 @RequestParam("jobId") String jobId,
                                 @RequestParam("skillId") String skillId){
        try {
            JobSkillId jobSkillId = new JobSkillId();
            jobSkillId.setJobId(jobRepository.findById(Long.parseLong(jobId)).get().getId());
            jobSkillId.setSkillId(skillRepository.findById(Long.parseLong(skillId)).get().getId());
            jobSkillRepository.deleteById(jobSkillId);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/jobDetail?jobId=" + jobId + "&companyId=" + companyId;
        }
        return "redirect:/jobDetail?jobId=" + jobId + "&companyId=" + companyId;
    }
}
