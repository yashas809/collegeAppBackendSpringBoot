package com.collegeManagement.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feestructure")
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class FeeStructureEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FeeStructurePK")
    @Getter
    private long feeStructurePK;

    @Getter
    @Setter
    @Column(name = "DeptFK")
    private long deptFK;

    @Getter
    @Setter
    @Column(name = "Sem")
    private int sem;

    @Getter
    @Setter
    @Column(name = "SemFee")
    private Double semFee;

}
