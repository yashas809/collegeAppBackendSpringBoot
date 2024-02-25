package com.collegeManagement.app.dao;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class StudentFee {

    private String usn;
    private int sem;
    private String deptName;
    private byte[] fileData;
    private String fileName;
    private boolean isFeePending;
    private long receiptFK;
    private String studentName;
    private double feePaid;
    private LocalDate createdDate;

}
