package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.repositories.CandidateRepository;
import com.nhietLab5.backend.repositories.CompanyRepository;
import com.nhietLab5.frontend.models.CandidateModel;
import com.nhietLab5.frontend.models.CompanyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @Autowired
    private CandidateModel candidateModel;
    @Autowired
    private CompanyModel companyModel;

    @GetMapping("/login")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/indexController")
    public ModelAndView showCandidateList(
            @RequestParam("id") String id,
            @RequestParam("role") String role) {
        ModelAndView modelAndView;
        Long iD = Long.parseLong(id);
        if ("candidate".equals(role)) {
            modelAndView = candidateModel.getCandidateByID(iD);
            return modelAndView;
        } else if ("company".equals(role)) {
            modelAndView = companyModel.getCompanyByID(iD);
            return modelAndView;
        }
        return null;
    }

    @GetMapping("/logout")
    public String logout() {
        return "index";
    }
}
