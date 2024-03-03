package com.collegeManagement.app.dao;


import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@Data
@AllArgsConstructor(staticName = "build")
public class TimeTableDAO
{
    private long timeTableId;
    private LocalTime fromTime;
    private LocalTime toTime;
    private String day;
    private String staffName;
    private String subjectName;
    private String deptName;
}
