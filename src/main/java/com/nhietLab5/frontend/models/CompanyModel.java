package com.nhietLab5.frontend.models;

import com.nhietLab5.backend.models.Company;
import com.nhietLab5.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CompanyModel {
    @Autowired
    private CompanyRepository companyRepository;

    public ModelAndView getCompanyByID(Long id){
        ModelAndView modelAndView;
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            modelAndView = new ModelAndView("redirect:/candidates?companyId=" + id);
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("index");
            modelAndView.addObject("error", "Company ID invalid.");
        }
        return modelAndView;
    }
}
