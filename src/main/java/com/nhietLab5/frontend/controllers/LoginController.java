package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.repositories.UserRepository;
import com.nhietLab5.frontend.models.CandidateModel;
import com.nhietLab5.frontend.models.CompanyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
        return "login";
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

//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/login";
//    }
}
