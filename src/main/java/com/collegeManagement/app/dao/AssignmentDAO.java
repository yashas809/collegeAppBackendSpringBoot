package com.collegeManagement.app.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class AssignmentDAO {

    private String subjectName;
    private String usn;
    private Boolean isAssignmentSubmitted;
}
