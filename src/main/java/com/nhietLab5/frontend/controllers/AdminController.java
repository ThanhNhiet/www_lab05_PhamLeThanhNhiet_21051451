package com.nhietLab5.frontend.controllers;

import com.nhietLab5.backend.enums.UserStatus;
import com.nhietLab5.backend.models.Job;
import com.nhietLab5.backend.models.JobSkill;
import com.nhietLab5.backend.models.User;
import com.nhietLab5.backend.repositories.JobRepository;
import com.nhietLab5.backend.repositories.JobSkillRepository;
import com.nhietLab5.backend.repositories.UserRepository;
import com.nhietLab5.backend.services.JobServices;
import com.nhietLab5.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JobServices jobServices;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @GetMapping("/userDashboard")
    public String showUsersPaging(Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  @RequestParam("adminId") Optional<Long> adminId) {
        Long adminID = adminId.orElse(null);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<User> userPage = userService.findAll(
                currentPage - 1, pageSize, "id", "asc", adminID);
        model.addAttribute("userPage", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("admin", userRepository.findById(adminID).orElse(null));
        return "admin/dashboard_user";
    }

    @PostMapping("/userDashboard/updateStatus")
    public String saveChanges(@RequestParam("adminId") Long adminId,
                              @RequestParam("userId") Long userId,
                              @RequestParam("statusInput") String status) {
        System.out.println("status: " + status);
        User user = userRepository.findById(userId).orElse(null);
        user.setStatus(UserStatus.valueOf(status.toUpperCase()));
        userRepository.save(user);
        return "redirect:/admin/userDashboard?adminId=" + adminId;
    }

    @GetMapping("/userDashboard/search")
    public String searchUser(Model model,
                             @RequestParam("adminId") Long adminId,
                             @RequestParam("email") String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            model.addAttribute("message", "User not found");
        }else{
            model.addAttribute("user", user);
        }
        model.addAttribute("admin", userRepository.findById(adminId).orElse(null));
        return "admin/dashboard_user";
    }

    @PostMapping("/saveNewAdmin")
    public String saveNewAdmin(@RequestParam("adminId") Long adminId,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("passwordConfirm") String passwordConfirm) {
        try {
            if (!password.equals(passwordConfirm)) {
                throw new Exception("Password and confirm password do not match");
            }
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setStatus(UserStatus.ACTIVE);
            user.setRole("ADMIN");
            userRepository.save(user);
            return "redirect:/admin/userDashboard?adminId=" + adminId;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/userDashboard?adminId=" + adminId + "&error=true";
        }
    }

    @GetMapping("/jobDashboard")
    public String showJobsPaging(Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  @RequestParam("adminId") Optional<Long> adminId) {
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

        Long id = adminId.orElse(null);
        model.addAttribute("admin", userRepository.findById(id).orElse(null));
        return "admin/dashboard_job";
    }

    @GetMapping("/jobDashboard/searchByJobID")
    public String searchJobByJobID(Model model,
                                   @RequestParam("adminId") Long adminId,
                                   @RequestParam("jobId") Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if(job == null) {
            model.addAttribute("message", "Job not found");
        }else{
            model.addAttribute("job", job);
        }
        model.addAttribute("admin", userRepository.findById(adminId).orElse(null));
        return "admin/dashboard_job";
    }

    @GetMapping("/jobDashboard/search")
    public String searchJob(Model model,
                            @RequestParam("adminId") Long adminId,
                            @RequestParam("companyName") String companyName,
                            @RequestParam("jobName") String jobName) {
        model.addAttribute("admin", userRepository.findById(adminId).orElse(null));
        if(companyName.equals("") && jobName.equals("")) {
            return "redirect:/admin/jobDashboard?adminId=" + adminId;
        }
        Job job = jobRepository.findByJobNameAndCompany_CompName(jobName, companyName);
        if(job == null) {
            model.addAttribute("message", "Job not found");
        }else{
            model.addAttribute("job", job);
        };
        return "admin/dashboard_job";
    }

    @GetMapping("/jobDashboard/deleteJob")
    public String deleteJob(@RequestParam("adminId") Long adminId,
                            @RequestParam("jobId") Long jobId) {
        List<JobSkill> jobSkills = jobSkillRepository.findAllByJobId(jobId);
        if (jobSkills != null) {
            for (JobSkill jobSkill : jobSkills) {
                jobSkillRepository.delete(jobSkill);
            }
        }
        jobRepository.findById(jobId).ifPresent(job -> {
            jobRepository.delete(job);
        });
        return "redirect:/admin/jobDashboard?adminId=" + adminId;
    }
}
