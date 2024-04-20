package com.collegeManagement.app.dao;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Component
public class Staff {

    private String name;
    private LocalDateTime createdDate;
    private Boolean delflag;
    private String departmentName;
    private String loginName;
    private String password;
    private String roleName;
    private long staffId;
}
