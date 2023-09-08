//package com.example.ttcn2etest.model.etity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//
//@Entity
//@Table(name = "course")
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Course {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @NotBlank
//    @Size(max = 100)
//    private String name;
//    @NotBlank
//    @Size(max = 2000)
//    private String description;
//    @NotBlank
//    @Size(max = 300)
//    @Column(name = "study_goals")
//    private String studyGoals;
//    private String schedule;
//    @Column(name = "number_teaching_sessions")
//    private int numberTeachingSessions;
//    private String curriculum;
//    @Column(name = "learn_online_or_offline")
//    @Enumerated(EnumType.STRING)
//    private Learn learnOnlineOrOffline;
//    @Column(name = "course_price", precision = 10, scale = 2)
//    private BigDecimal coursePrice;
//    @Column(name = "request_students")
//    private String requestStudents;
//    @Column(name = "created_date")
//    private Timestamp createdDate;
//    @Column(name = "update_date")
//    private Timestamp updateDate;
//    public enum Learn {
//        ONLINE, OFFLINE, ONLINE_AND_OFFLINE
//    }
//
////    @ManyToOne
////    @JoinColumn(name = "teacher_id")
////    private Teacher teacher;
////
////    @ManyToOne
////    @JoinColumn(name = "service_id")
////    private Service service;
//}
