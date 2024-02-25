package com.collegeManagement.app.dao;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
@Getter
@Setter
@Component
public class Login
{
    private String loginName;
    private String password;
    private LocalDateTime createdDate;
    private Boolean delflag;
    private String secretKey;
}
