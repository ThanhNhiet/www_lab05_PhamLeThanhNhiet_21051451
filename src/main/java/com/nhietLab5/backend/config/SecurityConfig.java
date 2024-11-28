package com.nhietLab5.backend.config;
import com.nhietLab5.backend.custom.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signUpCan", "/signUpComp", "/css/**",
                                "/js/**", "/signUpCan/saveCandidate", "/signUpComp/saveCompany",
                                "/signUpCan/sendVCode", "/signUpComp/sendVCode").permitAll()
                        .requestMatchers("/candidate/**").hasRole("CANDIDATE")
                        .requestMatchers("/company/**").hasRole("COMPANY")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginController")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        // Dong session ke ca khi thoat trinh duyet
        http.sessionManagement(session -> {
            session.sessionFixation(sessionFixation -> sessionFixation.migrateSession()); // bao mat session
            session.maximumSessions(1).maxSessionsPreventsLogin(false); // cho phep 1 session
        });

        return http.build();
    }
}