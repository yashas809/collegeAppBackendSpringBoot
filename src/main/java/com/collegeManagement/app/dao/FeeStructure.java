package com.collegeManagement.app.dao;


import lombok.*;
import org.springframework.stereotype.Service;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class FeeStructure {

    private String departmentName;
    private int sem;
    private double  semfee;

}
