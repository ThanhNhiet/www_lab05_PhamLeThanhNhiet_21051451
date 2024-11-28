package com.nhietLab5.backend.custom;

import com.nhietLab5.backend.enums.UserStatus;
import com.nhietLab5.backend.models.User;
import com.nhietLab5.backend.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Lấy email từ Authentication
        String email = authentication.getName();

        // Tìm thông tin User từ database
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null && user.getStatus().equals(UserStatus.ACTIVE)) {
            String redirectUrl;
            if ("CANDIDATE".equalsIgnoreCase(user.getRole())) {
                redirectUrl = "candidate/jobList?candidateId=" + user.getCandidate().getId();
            } else if ("COMPANY".equalsIgnoreCase(user.getRole())) {
                redirectUrl = "company/candidateList?companyId=" + user.getCompany().getId();
            } else {
                redirectUrl = "/dashboard";
            }

            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}
