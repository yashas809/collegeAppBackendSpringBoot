package com.collegeManagement.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Entity
@Table(name = "externalmarks")
public class ExternalMarksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExternalMarksPK")
    private long externalMarksPK;

    @Column(name = "USN")
    private String usn;

    @Column(name = "SubjectFK")
    private long subjectFK;

    @Column(name = "InternalMarksFK")
    private long internalMarksFK;

    @Column(name = "ExternalMarksMaximum")
    private int externalMarksMaximum;

    @Column(name = "ExternalMarksScored")
    private double externalMarksScored;

    @Column(name = "Sem")
    private int sem;

    @Column(name = "DeptFK")
    private long deptFK;
}
