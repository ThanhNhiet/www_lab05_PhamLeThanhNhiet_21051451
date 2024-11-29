package com.nhietLab5.backend.services;

import com.nhietLab5.backend.enums.UserStatus;
import com.nhietLab5.backend.models.Candidate;
import com.nhietLab5.backend.models.Company;
import com.nhietLab5.backend.models.User;
import com.nhietLab5.backend.repositories.CandidateRepository;
import com.nhietLab5.backend.repositories.CompanyRepository;
import com.nhietLab5.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerCandidate(Candidate candidate, String email, String password) {
        candidateRepository.save(candidate);

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("CANDIDATE");
        user.setCandidate(candidate);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public void registerCompany(Company company, String email, String password) {
        companyRepository.save(company);

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("COMPANY");
        user.setCompany(company);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public Page<User> findAll(int pageNo, int pageSize, String sortBy, String sortDirection, Long adminId) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return userRepository.findAllByIdNot(adminId, pageable);
    }


}