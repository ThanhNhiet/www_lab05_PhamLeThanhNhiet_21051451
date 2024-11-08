package com.nhietLab5;

import com.neovisionaries.i18n.CountryCode;
import com.nhietLab5.backend.enums.SkillLevel;
import com.nhietLab5.backend.enums.SkillType;
import com.nhietLab5.backend.models.*;
import com.nhietLab5.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class LabWeek05Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(LabWeek05Application.class, args);
    }

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Override
    public void run(String... args) throws Exception {
//        Random rnd = new Random();
//        for (int i = 1; i < 1000; i++) {
//            Address add = new Address();
//            add.setCity("HCM");
//            add.setCountry(CountryCode.VN);
//            add.setNumber(rnd.nextInt(1,1000)+"");
//            add.setStreet("Quang Trung");
//            add.setZipcode(rnd.nextInt(70000,80000)+"");
//            addressRepository.save(add);
//
//            Candidate can = new Candidate();
//            can.setAddress(add);
//            can.setDob(LocalDate.of(1998, rnd.nextInt(1, 13), rnd.nextInt(1, 29)));
//            can.setEmail("email_" + i + "@gmail.com");
//            can.setFullName("Name #" + i);
//            can.setPhone(rnd.nextLong(1111111111L, 9999999999L) + "");
//            candidateRepository.save(can);
//
//            Company com = new Company();
//            com.setAddress(add);
//            com.setAbout("About company " + i);
//            com.setCompName("Company " + i);
//            com.setEmail("company" + i + "@gmail.com");
//            com.setPhone(rnd.nextLong(1111111111L, 9999999999L) + "");
//            com.setWebUrl("http://company" + i + ".com");
//            companyRepository.save(com);
//
//            Job job = new Job();
//            job.setCompany(com);
//            job.setJobDesc("Job description " + i);
//            job.setJobName("Job name " + i);
//            jobRepository.save(job);
//
//            Skill skill = new Skill();
//            skill.setSkillDescription("Skill description " + i);
//            skill.setSkillName("Skill name " + i);
//            int tmpNum = rnd.nextInt(1, 4);
//            switch (tmpNum) {
//                case 1:
//                    skill.setType(SkillType.SOFT_SKILL);
//                    break;
//                case 2:
//                    skill.setType(SkillType.UNSPECIFIC);
//                    break;
//                case 3:
//                    skill.setType(SkillType.TECHNICAL_SKILL);
//                    break;
//            }
//            skillRepository.save(skill);
//        }
//
//        for (int i = 0; i <= 100; i++) {
//            // Tạo một CandidateSkillId mới
//            CandidateSkillId candidateSkillId = new CandidateSkillId();
//            candidateSkillId.setCanId(candidateRepository.findById((long) rnd.nextInt(1, 1000)).get().getId());
//            candidateSkillId.setSkillId(skillRepository.findById((long) rnd.nextInt(1, 1000)).get().getId());
//
//            // Tạo một CandidateSkill mới và đặt CandidateSkillId vào nó
//            CandidateSkill canSkill = new CandidateSkill();
//            canSkill.setId(candidateSkillId);
//
//            // Thiết lập các thuộc tính khác
//            canSkill.setCan(candidateRepository.findById(candidateSkillId.getCanId()).get());
//            canSkill.setSkill(skillRepository.findById(candidateSkillId.getSkillId()).get());
//            canSkill.setMoreInfos("More info " + i);
//
//            int tmpLevel = rnd.nextInt(1, 6);
//            switch (tmpLevel) {
//                case 1:
//                    canSkill.setSkillLevel(SkillLevel.ADVANCED);
//                    break;
//                case 2:
//                    canSkill.setSkillLevel(SkillLevel.BEGINNER);
//                    break;
//                case 3:
//                    canSkill.setSkillLevel(SkillLevel.MASTER);
//                    break;
//                case 4:
//                    canSkill.setSkillLevel(SkillLevel.PROFESSIONAL);
//                    break;
//                case 5:
//                    canSkill.setSkillLevel(SkillLevel.INTERMEDIATE);
//                    break;
//            }
//            candidateSkillRepository.save(canSkill);
//
//            JobSkillId jobSkillId = new JobSkillId();
//            jobSkillId.setJobId(jobRepository.findById((long) rnd.nextInt(1, 1000)).get().getId());
//            jobSkillId.setSkillId(skillRepository.findById((long) rnd.nextInt(1, 1000)).get().getId());
//
//            JobSkill jobSkill = new JobSkill();
//            jobSkill.setId(jobSkillId);
//            jobSkill.setJob(jobRepository.findById(jobSkillId.getJobId()).get());
//            jobSkill.setSkill(skillRepository.findById(jobSkillId.getSkillId()).get());
//            jobSkill.setMoreInfos("More info " + i);
//            int tmpLevel2 = rnd.nextInt(1, 6);
//            switch (tmpLevel2) {
//                case 1:
//                    jobSkill.setSkillLevel(SkillLevel.ADVANCED);
//                    break;
//                case 2:
//                    jobSkill.setSkillLevel(SkillLevel.BEGINNER);
//                    break;
//                case 3:
//                    jobSkill.setSkillLevel(SkillLevel.MASTER);
//                    break;
//                case 4:
//                    jobSkill.setSkillLevel(SkillLevel.PROFESSIONAL);
//                    break;
//                case 5:
//                    jobSkill.setSkillLevel(SkillLevel.INTERMEDIATE);
//                    break;
//            }
//            jobSkillRepository.save(jobSkill);
//        }
//        List<Candidate> candidates = candidateRepository.findCandidatesSuitableForJob((long) 1);
//        for (Candidate candidate : candidates) {
//            System.out.println(candidate.getFullName());
//        }
    }
}