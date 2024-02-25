package com.collegeManagement.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Table(name = "staff")
@Component
public class StaffEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "StaffID")
    private long staffId;

    @Getter
    @Setter
    @Column(name = "Name")
    private String name;

    @Getter
    @Setter
    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Getter
    @Setter
    @Column(name = "DelFlag")
    private Boolean delflag;

    @Getter
    @Setter
    @Column(name = "DeptFK")
    private long deptfk;

    @Getter
    @Setter
    @Column(name = "LoginFK")
    private long loginfk;

    @Getter
    @Setter
    @Column(name = "RoleFK")
    private long rolefk;
}
