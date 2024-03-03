package com.collegeManagement.app.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "build")
public class InternalMarksDAO {

    private String usn;
    private String subjectName;
    private int maxMarks;
    private double marksScored;
}
