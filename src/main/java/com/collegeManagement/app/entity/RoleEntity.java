package com.collegeManagement.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Component
public class RoleEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long rolePK;

    @NotBlank
    @Getter
    @Setter
    @Column(name = "RoleName")
    private String roleName;
}
