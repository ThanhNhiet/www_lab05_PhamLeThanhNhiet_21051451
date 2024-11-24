package com.nhietLab5.backend.custom;

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

        if (user != null) {
            String redirectUrl;

            // Chuyển hướng theo vai trò
            if ("CANDIDATE".equalsIgnoreCase(user.getRole())) {
                redirectUrl = "/jobs/list?candidateId=" + user.getCandidate().getId();
            } else if ("COMPANY".equalsIgnoreCase(user.getRole())) {
                redirectUrl = "/candidates/list?companyId=" + user.getCompany().getId();
            } else {
                redirectUrl = "/dashboard"; // Mặc định nếu không xác định được vai trò
            }

            response.sendRedirect(redirectUrl);
        } else {
            // Nếu không tìm thấy User, quay lại trang login với thông báo lỗi
            response.sendRedirect("/login?error=true");
        }
    }
}
