package com.collegeManagement.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "subject")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubjectPK")
    private long subjectPK;

    @Column(name = "SubjectName")
    private String subjectName;

    @Column(name = "SubjectCode")
    private String subjectCode;

    @Column(name = "DeptFK")
    private long deptFK;

    @Column(name = "Sem")
    private int sem;

}
