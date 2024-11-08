package com.nhietLab5.backend.repositories;

import com.nhietLab5.backend.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findCompanyByCompName(String companyName);
}