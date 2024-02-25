package com.collegeManagement.app.dao;

import com.collegeManagement.app.enums.studentEnum;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Getter
@Setter
public class Student
{
    private String usn;
    private int sem;
    private String departmentName;
    private String loginName;
    private String status;
    private String studentName;
    private LocalDate dateOfBirth;
    private String gender;
    private String emailID;
    private String password;
    private long studentID;
}
