package com.nhietLab5;

import com.neovisionaries.i18n.CountryCode;
import com.nhietLab5.backend.enums.SkillLevel;
import com.nhietLab5.backend.enums.SkillType;
import com.nhietLab5.backend.enums.UserStatus;
import com.nhietLab5.backend.models.*;
import com.nhietLab5.backend.repositories.*;
import com.nhietLab5.backend.services.SkillsRecommendServices;
import com.nhietLab5.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
public class LabWeek05Application implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @Autowired
    private SkillsRecommendServices skillsRecommendServices;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
//        //Admin
//        User admin = new User();
//        admin.setEmail("connect247@gmail.com");
//        admin.setPassword("123456");
//        admin.setRole("ADMIN");
//        userService.saveAdmin(admin);
        
//        //Address
//        Address address1 = new Address("lien ap 123", "HCM", CountryCode.VN, "123", "70000");
//        addressRepository.save(address1);
//        Address address2 = new Address("lien ap 456", "HCM", CountryCode.VN, "456", "70000");
//        addressRepository.save(address2);
//        Address address3 = new Address("lien ap 789", "HCM", CountryCode.VN, "789", "70000");
//        addressRepository.save(address3);
//
//        //Candidate
//        LocalDate dob1= LocalDate.of(2003, 11, 15);
//        Candidate candidate1 = new Candidate(dob1, "nhietdizz@gmail.com", "Pham Le Thanh Nhiet", "0901234567", address1);
////        candidateRepository.save(candidate1);
//        userService.registerCandidate(candidate1, "nhietdizz@gmail.com", "123456");
//
//        LocalDate dob2= LocalDate.of(2000, 11, 15);
//        Candidate candidate2 = new Candidate(dob2, "ahaha@gmail.com", "Tran Ha", "0901134567", address2);
////        candidateRepository.save(candidate2);
//        userService.registerCandidate(candidate2, "ahaha@gmail.com", "111111");
//
//        LocalDate dob3= LocalDate.of(2002, 11, 15);
//        Candidate candidate3 = new Candidate(dob3, "en@gmail.com", "Nguyen em", "0901134569", address3);
////        candidateRepository.save(candidate3);
//        userService.registerCandidate(candidate3, "en@gmail.com", "222222");
//
//        //Experience
//        LocalDate fromDate1 = LocalDate.of(2023, 1, 1);
//        LocalDate toDate1 = LocalDate.of(2024, 1, 1);
//        Experience experience1 = new Experience(fromDate1, toDate1, candidate1, "Quang Trung", "Intern", "none");
//        experienceRepository.save(experience1);
//
//        //Skill
//        Skill skill1 = new Skill("none", "Java", SkillType.TECHNICAL_SKILL);
//        skillRepository.save(skill1);
//        Skill skill3 = new Skill("none", "C++", SkillType.TECHNICAL_SKILL);
//        skillRepository.save(skill3);
//        Skill skill4 = new Skill("none", "C#", SkillType.TECHNICAL_SKILL);
//        skillRepository.save(skill4);
//        Skill skill7 = new Skill("none", "Javascript", SkillType.TECHNICAL_SKILL);
//        skillRepository.save(skill7);
//        Skill skill8 = new Skill("none", "HTML", SkillType.TECHNICAL_SKILL);
//        skillRepository.save(skill8);
//        Skill skill9 = new Skill("none", "CSS", SkillType.TECHNICAL_SKILL);
//        skillRepository.save(skill9);
//        Skill skill10 = new Skill("none", "SQL", SkillType.TECHNICAL_SKILL);
//        skillRepository.save(skill10);
//        Skill skill2 = new Skill("none", "Teamwork", SkillType.SOFT_SKILL);
//        skillRepository.save(skill2);
//        Skill skill5 = new Skill("none", "Communication", SkillType.SOFT_SKILL);
//        skillRepository.save(skill5);
//        Skill skill6 = new Skill("none", "Problem solving", SkillType.SOFT_SKILL);
//        skillRepository.save(skill6);
//
//        //CandidateSkill
//        CandidateSkillId candidateSkillId1 = new CandidateSkillId(candidate1.getId(), skill1.getId());
//        CandidateSkill candidateSkill1 = new CandidateSkill(candidateSkillId1, candidate1, skill1, "none", SkillLevel.ADVANCED);
//        candidateSkillRepository.save(candidateSkill1);
//        CandidateSkillId candidateSkillId2 = new CandidateSkillId(candidate1.getId(), skill2.getId());
//        CandidateSkill candidateSkill2 = new CandidateSkill(candidateSkillId2, candidate1, skill2, "none", SkillLevel.ADVANCED);
//        candidateSkillRepository.save(candidateSkill2);
//        CandidateSkillId candidateSkillId3 = new CandidateSkillId(candidate1.getId(), skill8.getId());
//        CandidateSkill candidateSkill3 = new CandidateSkill(candidateSkillId3, candidate1, skill8, "none", SkillLevel.BEGINNER);
//        candidateSkillRepository.save(candidateSkill3);
//        CandidateSkillId candidateSkillId4 = new CandidateSkillId(candidate1.getId(), skill10.getId());
//        CandidateSkill candidateSkill4 = new CandidateSkill(candidateSkillId4, candidate1, skill10, "none", SkillLevel.ADVANCED);
//        candidateSkillRepository.save(candidateSkill4);
//        CandidateSkillId candidateSkillId5 = new CandidateSkillId(candidate1.getId(), skill5.getId());
//        CandidateSkill candidateSkill5 = new CandidateSkill(candidateSkillId5, candidate1, skill5, "none", SkillLevel.ADVANCED);
//        candidateSkillRepository.save(candidateSkill5);
//
//        CandidateSkillId candidateSkillId6 = new CandidateSkillId(candidate2.getId(), skill1.getId());
//        CandidateSkill candidateSkill6 = new CandidateSkill(candidateSkillId6, candidate2, skill1, "none", SkillLevel.MASTER);
//        candidateSkillRepository.save(candidateSkill6);
//        CandidateSkillId candidateSkillId7 = new CandidateSkillId(candidate2.getId(), skill2.getId());
//        CandidateSkill candidateSkill7 = new CandidateSkill(candidateSkillId7, candidate2, skill2, "none", SkillLevel.PROFESSIONAL);
//        candidateSkillRepository.save(candidateSkill7);
//        CandidateSkillId candidateSkillId8 = new CandidateSkillId(candidate2.getId(), skill8.getId());
//        CandidateSkill candidateSkill8 = new CandidateSkill(candidateSkillId8, candidate2, skill10, "none", SkillLevel.ADVANCED);
//        candidateSkillRepository.save(candidateSkill8);
//
//        CandidateSkillId candidateSkillId9 = new CandidateSkillId(candidate3.getId(), skill1.getId());
//        CandidateSkill candidateSkill9 = new CandidateSkill(candidateSkillId9, candidate3, skill1, "none", SkillLevel.BEGINNER);
//        candidateSkillRepository.save(candidateSkill9);
//        CandidateSkillId candidateSkillId10 = new CandidateSkillId(candidate3.getId(), skill10.getId());
//        CandidateSkill candidateSkill10 = new CandidateSkill(candidateSkillId10, candidate3, skill10, "none", SkillLevel.MASTER);
//        candidateSkillRepository.save(candidateSkill10);
//        CandidateSkillId candidateSkillId11 = new CandidateSkillId(candidate3.getId(), skill5.getId());
//        CandidateSkill candidateSkill11 = new CandidateSkill(candidateSkillId11, candidate3, skill5, "none", SkillLevel.PROFESSIONAL);
//        candidateSkillRepository.save(candidateSkill11);
//        CandidateSkillId candidateSkillId12 = new CandidateSkillId(candidate3.getId(), skill6.getId());
//        CandidateSkill candidateSkill12 = new CandidateSkill(candidateSkillId12, candidate3, skill6, "none", SkillLevel.PROFESSIONAL);
//        candidateSkillRepository.save(candidateSkill12);
//
//        Company com1 = new Company();
//        com1.setAddress(address3);
//        com1.setAbout("About company");
//        com1.setCompName("Green Tech");
//        com1.setEmail("nhiethiz@gmail.com");
//        com1.setPhone("0901234567");
//        com1.setWebUrl("http://GreenT.com.vn");
////        companyRepository.save(com1);
//        userService.registerCompany(com1, "nhiethiz@gmail.com", "123123");
//
//        //job
//        Job job1 = new Job("none", "Java dev", companyRepository.findById(1L).get());
//        jobRepository.save(job1);
//        Job job2 = new Job("none", "BA", companyRepository.findById(1L).get());
//        jobRepository.save(job2);
//        Job job3 = new Job("none", "Tester", companyRepository.findById(1L).get());
//        jobRepository.save(job3);
//
//        //JobSkill
//        JobSkillId jobSkillId1 = new JobSkillId(job1.getId(), skill1.getId());
//        JobSkill jobSkill1 = new JobSkill(jobSkillId1, job1, skill1, "none", SkillLevel.ADVANCED);
//        jobSkillRepository.save(jobSkill1);
//        JobSkillId jobSkillId2 = new JobSkillId(job1.getId(), skill10.getId());
//        JobSkill jobSkill2 = new JobSkill(jobSkillId2, job1, skill10, "none", SkillLevel.ADVANCED);
//        jobSkillRepository.save(jobSkill2);
//        JobSkillId jobSkillId3 = new JobSkillId(job2.getId(), skill5.getId());
//        JobSkill jobSkill3 = new JobSkill(jobSkillId3, job2, skill5, "none", SkillLevel.MASTER);
//        jobSkillRepository.save(jobSkill3);
//        JobSkillId jobSkillId4 = new JobSkillId(job2.getId(), skill6.getId());
//        JobSkill jobSkill4 = new JobSkill(jobSkillId4, job2, skill6, "none", SkillLevel.PROFESSIONAL);
//        jobSkillRepository.save(jobSkill4);
//        JobSkillId jobSkillId5 = new JobSkillId(job3.getId(), skill1.getId());
//        JobSkill jobSkill5 = new JobSkill(jobSkillId5, job3, skill1, "none", SkillLevel.MASTER);
//        jobSkillRepository.save(jobSkill5);
//
//        Random rnd = new Random();
//        for (int i = 1; i <= 100; i++) {
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
//            Experience exp = new Experience();
//            exp.setCandidate(can);
//            exp.setCompanyName("Company " + i);
//            exp.setFromDate(LocalDate.of(rnd.nextInt(2000, 2023), rnd.nextInt(1, 13), rnd.nextInt(1, 29)));
//            exp.setToDate(LocalDate.of(rnd.nextInt(2000, 2023), rnd.nextInt(1, 13), rnd.nextInt(1, 29)));
//            exp.setRole("Role " + i);
//            exp.setWorkDescription("Work description " + i);
//            experienceRepository.save(exp);
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
//        }
//
//        for (int i = 0; i <= 100; i++) {
//            CandidateSkillId candidateSkillId = new CandidateSkillId();
//            candidateSkillId.setCanId(candidateRepository.findById((long) rnd.nextInt(1, 1000)).get().getId());
//            candidateSkillId.setSkillId(skillRepository.findById((long) rnd.nextInt(1, 1000)).get().getId());
//
//            CandidateSkill canSkill = new CandidateSkill();
//            canSkill.setId(candidateSkillId);
//
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

//         //Test query
//        List<Candidate> candidates = candidateRepository.findLSuitableCandidatesForJob("BA", 1L);
//        for (Candidate candidate : candidates) {
//            System.out.println(candidate.getFullName());
//        }
//        List<Job> jobs = jobRepository.findSuitableJobsForCandidate(777L);
//        for (Job job : jobs) {
//            System.out.println(job.getJobName());
//        }
//        List<Skill> skills = skillRepository.findMissingSkillsForCandidateByJobAndCompany("Job name 1", "Company 1", 14L);
//        for (Skill skill : skills) {
//            System.out.println(skill.getSkillName());
//        }
//        boolean isPasswordMatch = passwordEncoder.matches("1234567", "$2a$10$aWFut0lcbIt6tAX5y0nlgOrR4ZXbuv.3C08jJHYlNXgtNzJqS92au");
//        System.out.println("Mật khẩu có hợp lệ không? " + isPasswordMatch);
    }
}