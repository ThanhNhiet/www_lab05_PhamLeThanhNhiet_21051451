package com.nhietLab5.frontend.models;

import com.nhietLab5.backend.models.Candidate;
import com.nhietLab5.backend.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CandidateModel {
    @Autowired
    private CandidateRepository candidateRepository;

    public ModelAndView getCandidateByID(Long id){
        ModelAndView modelAndView;
        Candidate candidate = candidateRepository.findById(id).orElse(null);
        if (candidate != null) {
            modelAndView = new ModelAndView("redirect:/jobs/list?candidateId=" + id);
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Candidate ID invalid.");
        }
        return modelAndView;
    }
}
