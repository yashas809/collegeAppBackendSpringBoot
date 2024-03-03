package com.collegeManagement.app.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "build")
public class ExternalMarksDAO {
    private String usn;
    private String subjectName;
    private int externalMarksMaximum;
    private double externalMarksScored;
    private int sem;
    private String departmentName;
    private double internalMarksScored;
    private int internalMaxmimumMarks;
}
