package com.collegeManagement.app.dao;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Setter
@Getter
public class Admin {

    private String username;
    private String roleName;
    private String password;
    private String loginName;

}
