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
//import java.sql.Timestamp;
//
//@Entity
//@Table(name = "teacher")
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Teacher {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @NotBlank
//    @Size(max = 100)
//    private String name;
//    @NotBlank
//    private String phone;
//    @NotBlank
//    private String email;
//    @Size(max = 1000)
//    private String description;
//
//    @Column(name = "created_date")
//    private Timestamp createdDate;
//    @Column(name = "update_date")
//    private Timestamp updateDate;
//
////    @OneToMany(mappedBy = "teacher")
////    private Set<Service> services;
////
////    @OneToMany(mappedBy = "teacher")
////    private Set<Course> courses;
//}
