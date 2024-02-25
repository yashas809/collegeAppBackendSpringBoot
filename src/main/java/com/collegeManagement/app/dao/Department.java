package com.collegeManagement.app.dao;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private long deptId;

    @NotBlank
    private String departmentName;

}
