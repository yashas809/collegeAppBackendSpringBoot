package com.collegeManagement.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "TimeTable")
@Getter
@Setter
public class TimeTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TimeTablePK")
    private long timeTablePK;

    @Column(name = "FromTime")
    private LocalTime fromTime;

    @Column(name = "ToTime")
    private LocalTime toTime;

    @Column(name = "Day")
    private String day;

    @Column(name = "StaffFK")
    private Long staffFK;

    @Column(name = "SubjectFK")
    private Long subjectFK;

    @Column(name = "DeptFK")
    private Long deptFK;

    @Column(name = "Sem")
    private int sem;

}
