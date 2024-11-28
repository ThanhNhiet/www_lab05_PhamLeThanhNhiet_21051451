package com.nhietLab5.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import com.nhietLab5.backend.models.Address;
import com.nhietLab5.backend.models.Candidate;
import com.nhietLab5.backend.models.Company;
import com.nhietLab5.backend.models.Experience;
import com.nhietLab5.backend.repositories.AddressRepository;
import com.nhietLab5.backend.repositories.CandidateSkillRepository;
import com.nhietLab5.backend.repositories.ExperienceRepository;
import com.nhietLab5.backend.services.EmailSenderServices;
import com.nhietLab5.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class SignUpController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private EmailSenderServices emailSenderServices;

    private static String vCodeCan;
    private static String vCodeComp;

    @GetMapping("/signUpCan")
    public String showSignUpCan() {
        return "signUp/signUp_candidate";
    }

    @GetMapping("/signUpComp")
    public String showSignUpComp() {
        return "signUp/signUp_Company";
    }

    private String randomVCode() {
        Random random = new Random();
        int vCode = 100000 + random.nextInt(900000);
        return String.valueOf(vCode);
    }

    @PostMapping("/signUpCan/sendVCode")
    @ResponseBody
    public Map<String, Object> sendVCode(@RequestParam("canEmail") String email) {
        vCodeCan = randomVCode();
        boolean emailSent = emailSenderServices.sendEmail("nhiethiz@gmail.com", email, "Verification Code", vCodeCan);

        Map<String, Object> response = new HashMap<>();
        if (emailSent) {
            response.put("success", true);
            response.put("message", "Verification code sent successfully!");
        } else {
            response.put("success", false);
            response.put("message", "Failed to send verification code.");
        }
        return response;
    }

    @PostMapping("/signUpComp/sendVCode")
    @ResponseBody
    public Map<String, Object> sendVCodeComp(@RequestParam("compEmail") String email) {
        System.out.println("email: " + email);
        vCodeComp = randomVCode();
        boolean emailSent = emailSenderServices.sendEmail("nhiethiz@gmail.com", email, "Verification Code", vCodeComp);

        Map<String, Object> response = new HashMap<>();
        if (emailSent) {
            response.put("success", true);
            response.put("message", "Verification code sent successfully!");
        } else {
            response.put("success", false);
            response.put("message", "Failed to send verification code.");
        }
        return response;
    }

    @PostMapping("/signUpCan/saveCandidate")
    public String saveCandidateUser(@RequestParam String fullName,
                                    @RequestParam String dob,
                                    @RequestParam String phone,
                                    @RequestParam String email,
                                    @RequestParam String verCode,
                                    @RequestParam String password,
                                    @RequestParam String confirmPassword,
                                    @RequestParam String city,
                                    @RequestParam String zip,
                                    @RequestParam String address,
                                    @RequestParam String number,
                                    @RequestParam String companyName,
                                    @RequestParam String role,
                                    @RequestParam String startDate,
                                    @RequestParam String endDate,
                                    @RequestParam String description) {

        System.out.println("fullName: " + fullName);
        System.out.println("vercode: " + verCode);
        if(!verCode.equals(vCodeCan)) {
            return "redirect:/signUpCan?error=invalidVerificationCode";
        }

        if (!password.equals(confirmPassword)) {
            return "redirect:/signUpCan?error=passwordsDoNotMatch";
        }

        LocalDate birthDate = LocalDate.parse(dob);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        Address addressEntity = new Address(address, city, CountryCode.VN, zip, number);
        addressRepository.save(addressEntity);

        Candidate candidate = new Candidate(birthDate, email, fullName, phone, addressEntity);
        userService.registerCandidate(candidate, email, password);

        Experience experience = new Experience(start, end, candidate, companyName, role, description);
        experienceRepository.save(experience);

        return "redirect:/login?success=true";
    }

    @PostMapping("/signUpComp/saveCompany")
    public String saveCompanyUser(@RequestParam String compName,
                                  @RequestParam String webUrl,
                                  @RequestParam String phone,
                                  @RequestParam String about,
                                  @RequestParam String email,
                                  @RequestParam String verCode,
                                  @RequestParam String password,
                                  @RequestParam String confirmPassword,
                                  @RequestParam String city,
                                  @RequestParam String zip,
                                  @RequestParam String street,
                                  @RequestParam String number) {

        if(!verCode.equals(vCodeComp)) {
            return "redirect:/signUpComp?error=invalidVerificationCode";
        }

        if (!password.equals(confirmPassword)) {
            return "redirect:/signUpComp?error=passwordsDoNotMatch";
        }

        Address addressEntity = new Address(street, city, CountryCode.VN, zip, number);
        addressRepository.save(addressEntity);

        Company company = new Company();
        company.setCompName(compName);
        company.setWebUrl(webUrl);
        company.setPhone(phone);
        company.setEmail(email);
        company.setAbout(about);
        company.setAddress(addressEntity);
        userService.registerCompany(company, email, password);

        return "redirect:/login?success=true";
    }
}
