package com.collegeManagement.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attendance")
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Getter
@Setter
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AttendancePK")
    private long attendancePK;

    @Column(name = "USN")
    private String usn;

    @Column(name = "SubjectFK")
    private long subjectFK;

    @Column(name = "Sem")
    private int sem;

    @Column(name = "TotalNumberofClasses")
    private long totalNumberOfClasses;

    @Column(name = "AttendedClasses")
    private long attendedClasses;

    @Column(name = "AttendancePercentage")
    private double attendancePercentage;

}
