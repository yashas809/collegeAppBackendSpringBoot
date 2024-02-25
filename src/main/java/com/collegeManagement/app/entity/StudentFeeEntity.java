package com.collegeManagement.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "studentfee")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class StudentFeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "StudentFeePK")
    private long StudentFeePK;

    @Getter
    @Setter
    @Column(name ="FeeFK")
    private long feeFK;

    @Getter
    @Setter
    @Column(name ="StudentFK")
    private long studentFK;

    @Getter
    @Setter
    @Column(name ="isFeePending")
    private boolean isFeePending;


    @Getter
    @Setter
    @Column(name ="TotalFeePaid")
    private double totalFeePaid;


    @Getter
    @Setter
    @Column(name ="ReceiptFK")
    private double receiptFK;

    @Getter
    @Setter
    @Column(name ="CreatedDate")
    private LocalDate createdDate;

}
