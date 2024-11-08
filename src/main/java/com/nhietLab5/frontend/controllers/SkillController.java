package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.models.Skill;
import com.nhietLab5.backend.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SkillController {
    @Autowired
    private SkillRepository skillRepository;
    @GetMapping("/skills")
    @ResponseBody
    public List<String> getSkillNames() {
        List<Skill> skills = skillRepository.findAll(); // Lấy tất cả các kỹ năng từ cơ sở dữ liệu
        return skills.stream()
                .map(Skill::getSkillName)  // Chỉ lấy tên kỹ năng
                .collect(Collectors.toList());
    }
}
