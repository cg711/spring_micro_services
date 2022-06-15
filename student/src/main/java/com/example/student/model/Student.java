package com.example.student.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "ssn")
    private String ssn;

    @Column(name = "grade")
    private String grade;

//    @Column(name = "tests")
//    @OneToMany(mappedBy = "studentAccount", cascade = CascadeType.ALL)
//    private List<Test> tests;
}
