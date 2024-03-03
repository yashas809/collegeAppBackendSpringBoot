package com.collegeManagement.app.dao;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class SubjectDAO
{
    private String subjectName;
    private String subjectCode;
    private String deptName;
    private int sem;
}
