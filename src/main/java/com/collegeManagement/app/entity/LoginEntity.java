package com.collegeManagement.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "login")
@Component
public class LoginEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "LoginPK")
    private Long loginPK;

    @Getter
    @Setter
    @Column(name = "LoginName")
    private String loginName;

    @Getter
    @Setter
    @Column(name = "Password")
    private String password;

    @Getter
    @Setter
    @Column(name = "Createddate")
    private LocalDateTime createddate;

    @Getter
    @Setter
    @Column(name = "DelFlag")
    private Boolean delFlag;

    @Getter
    @Setter
    @Column(name = "SecretKey")
    private String secretKey;
}
