package com.collegeManagement.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "feereceipt")
public class FeeReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "FeeReceiptPK")
    private long feeReceiptPK;

    @Getter
    @Setter
    @Column(name = "Receipt")
    @Lob
    private byte[] receiptFile;

    @Getter
    @Setter
    @Column(name = "fileName")
    private String fileName;

}
