package com.collegeManagement.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Table(name = "department")
@Component
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DeptPK")
    @Getter
    @Setter
    private long id;

    @NotBlank
    @Column(name = "DepartmentName")
    @Getter
    @Setter
    private String departmentName;

}
