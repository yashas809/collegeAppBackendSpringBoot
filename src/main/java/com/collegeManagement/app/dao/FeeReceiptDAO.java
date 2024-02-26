package com.collegeManagement.app.dao;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class FeeReceiptDAO {

    private byte[] fileStream;
    private String fileName;

}
