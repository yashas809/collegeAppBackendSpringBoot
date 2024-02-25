package com.collegeManagement.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Table(name = "admin")
public class AdminEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminPK")
    @Getter
    private long adminpk;

    @Getter
    @Setter
    @Column(name = "Name")
    private String name;

    @Getter
    @Setter
    @Column(name = "LoginFK")
    private long loginFK;

    @Getter
    @Setter
    @Column(name = "RoleFK")
    private long RoleFK;


}
