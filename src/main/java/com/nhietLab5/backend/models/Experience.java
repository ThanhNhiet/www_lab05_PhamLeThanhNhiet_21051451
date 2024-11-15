package com.nhietLab5.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id", nullable = false)
    private Long id;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "can_id", nullable = false)
    private Candidate candidate;

    @Column(name = "company", nullable = false, length = 120)
    private String companyName;

    @Column(name = "role", nullable = false, length = 100)
    private String role;

    @Column(name = "work_desc", nullable = true, length = 400)
    private String workDescription;

    public Experience() {
    }

    public Experience(LocalDate fromDate, LocalDate toDate, Candidate candidate, String companyName, String role, String workDescription) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.candidate = candidate;
        this.companyName = companyName;
        this.role = role;
        this.workDescription = workDescription;
    }
}
