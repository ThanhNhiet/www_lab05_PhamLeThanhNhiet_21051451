package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.models.Job;
import com.nhietLab5.backend.models.JobSkill;
import com.nhietLab5.backend.models.Skill;
import com.nhietLab5.backend.repositories.CandidateRepository;
import com.nhietLab5.backend.repositories.JobRepository;
import com.nhietLab5.backend.repositories.JobSkillRepository;
import com.nhietLab5.backend.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/skills")
public class SkillController {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/list")
    @ResponseBody
    public List<String> getSkillNames() {
        List<Skill> skills = skillRepository.findAll();
        return skills.stream()
                .map(Skill::getSkillName)
                .collect(Collectors.toList());
    }
}
