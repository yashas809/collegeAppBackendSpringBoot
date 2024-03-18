package com.collegeManagement.app.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "build")
public class AttendanceDAO
{
    private long id;
    private String usn;
    private String subjectName;
    private int sem;
    private long totalNumberOfClasses;
    private long NoOfClassesAttended;
    private double AttendancePercentage;
}
