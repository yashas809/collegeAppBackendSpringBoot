package com.collegeManagement.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "StudentPK")
    private long studentPK;

    @Getter
    @Setter
    @Column(name = "USN")
    private String usn;

    @Getter
    @Setter
    @Column(name = "Sem")
    private int sem;

    @Getter
    @Setter
    @Column(name = "DeptFK")
    private long deptfk;

    @Getter
    @Setter
    @Column(name = "CreatedDate")
    private LocalDate createdDate;

    @Getter
    @Setter
    @Column(name = "LoginFK")
    private long loginFK;

    @Getter
    @Setter
    @Column(name = "RoleFK")
    private long roleFK;

    @Getter
    @Setter
    @Column(name = "Status")
    private int status;

    @Getter
    @Setter
    @Column(name = "StudentName")
    private String studentName;

    @Getter
    @Setter
    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;


    @Getter
    @Setter
    @Column(name = "Gender")
    private int gender;

    @Getter
    @Setter
    @Column(name = "StudentEmailID")
    private String studentEmailID;

}
