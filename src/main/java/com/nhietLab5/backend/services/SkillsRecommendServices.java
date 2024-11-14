package com.nhietLab5.backend.services;

import com.nhietLab5.backend.models.*;
import com.nhietLab5.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkillsRecommendServices {
    @Autowired
    private OpenAIServices openAIServices;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;

    public String prepareData(List<CandidateSkill> candidateSkills, List<Skill> skills,
                              List<JobSkill> jobSkills, String jobName){
        StringBuilder data = new StringBuilder("Data:\n");

        data.append("- All skills in database:\n");
        for (Skill skill : skills) {
            data.append("+ ").append(skill.getSkillName()).append("\n");
        }

        data.append("- Candidate skills:\n");
        for (CandidateSkill candidateSkill : candidateSkills) {
            String skillName = skillRepository.findById(candidateSkill.getSkill().getId()).get().getSkillName();
            data.append("+ skill: ").append(skillName)
                    .append(" (level: ").append(candidateSkill.getSkillLevel().toString())
                    .append(")\n");
        }

        data.append("- Job Name: ").append(jobName).append("\n");
        data.append("- Job skills:\n");
        for (JobSkill jobSkill : jobSkills) {
            String skillName = skillRepository.findById(jobSkill.getSkill().getId()).get().getSkillName();
            data.append("+ job skill: ").append(skillName)
                    .append(" (required level: ").append(jobSkill.getSkillLevel().toString())
                    .append(")\n");
        }

        return data.toString();
    }

    public Map<Long, String> recommendSkillsForCandidate(Long candidateId, String jobName, String companyName) throws Exception {
        String prompt = "Based on the following data, what skills should the candidate with ID " + candidateId +
                " learn to meet the requirements of the job '" + jobName +
                "' at company '" + companyName + "'?\n" +
                "Respone have format: Skill ID: ... - Reason: ...\n";

        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        Job job = jobRepository.findByJobNameAndCompany_CompName(jobName, companyName);
        List<Skill> skills = skillRepository.findAll();
        List<CandidateSkill> candidateSkills = candidateSkillRepository.findByCan(candidate);
        List<JobSkill> jobSkills = jobSkillRepository.findAllByJobId(job.getId());
        String preparedData = prepareData(candidateSkills, skills, jobSkills, jobName);
        prompt += preparedData;

        String response = openAIServices.getResponse(prompt);
//        System.out.println(response);

        // Long is skillID, String is reason
        Map<Long, String> recommendedSkills = new HashMap<>();
        for (String line : response.split("\n")) {
            if (line.contains("Skill ID") && line.contains("Reason")) {
                String[] parts = line.split(" - Reason: ");
                Long skillId = Long.parseLong(parts[0].split(": ")[1]);
                String reason = parts[1];
                recommendedSkills.put(skillId, reason);
            }
        }

        return recommendedSkills;
    }
}
