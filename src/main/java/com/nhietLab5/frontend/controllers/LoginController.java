package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.models.User;
import com.nhietLab5.backend.repositories.CandidateRepository;
import com.nhietLab5.backend.repositories.CompanyRepository;
import com.nhietLab5.backend.repositories.UserRepository;
import com.nhietLab5.frontend.models.CandidateModel;
import com.nhietLab5.frontend.models.CompanyModel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @Autowired
    private CandidateModel candidateModel;
    @Autowired
    private CompanyModel companyModel;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showIndex() {
        return "index";
    }

//    @PostMapping("/indexController")
//    public ModelAndView showCandidateList(
//            @RequestParam("email") String email,
//            @RequestParam("password") String password) {
//        ModelAndView modelAndView = null;
//        if (userRepository.existsByEmail(email)) {
//            User user = userRepository.findByEmail(email).orElse(null);
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                if (user.getRole().equals("CANDIDATE")) {
//                    modelAndView = candidateModel
//                            .getCandidateByID(user.getCandidate().getId());
//                    return modelAndView;
//                } else if(user.getRole().equals("COMPANY")) {
//                    modelAndView = companyModel.getCompanyByID(user.getCompany().getId());
//                    return modelAndView;
//                }
//            } else {
//                modelAndView = new ModelAndView("index");
//                modelAndView.addObject("error", "Password incorrect.");
//            }
//        } else {
//            modelAndView = new ModelAndView("index");
//            modelAndView.addObject("error", "Email not found.");
//        }
//        return modelAndView;
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/login";
//    }
}
