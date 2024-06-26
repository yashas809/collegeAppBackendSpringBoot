package com.collegeManagement.app.controller;


import com.collegeManagement.app.dao.FeeReceiptDAO;
import com.collegeManagement.app.dao.StudentFee;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.exception.UnsupportedFormat;
import com.collegeManagement.app.service.IStudentFeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/student/fee")
@CrossOrigin(origins = "*")
public class StudentFeeController {

    @Autowired
    IStudentFeeService feeService;

    @Autowired
    InputException ioexp;

    @Autowired
    UnsupportedFormat unsupportedFormat;

    @Autowired
    NoDataFoundException noDataFoundException;

    @PostMapping("/add")
    public ResponseEntity addFee(@RequestParam("data") String requestData, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            if (Objects.equals(file.getContentType(), "application/pdf")) {
                try {
                    JSONObject convertedJsonData = new JSONObject(requestData);
                    StudentFee request = new StudentFee();
                    request.setFeePaid(convertedJsonData.getDouble("feePaid"));
                    request.setUsn(convertedJsonData.getString("usn"));
                    request.setDeptName(convertedJsonData.getString("deptName"));
                    request.setFeePending(convertedJsonData.getBoolean("isFeePending"));
                    request.setSem(convertedJsonData.getInt("sem"));
                    request.setFileData(file.getInputStream().readAllBytes());
                    request.setFileName(file.getOriginalFilename());
                    StudentFee response = feeService.createEntry(request);
                    if (response != null) {
                        return ResponseEntity.ok(response);
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            } else {
                return ResponseEntity.badRequest().body(unsupportedFormat);
            }
        }
        return ResponseEntity.status(422).body(ioexp);
    }


    @GetMapping("/getbystudentusn")
    public ResponseEntity getfeeByStudentusn(@RequestParam("usn") String usn) {
        List<StudentFee> response = feeService.getStudentFeeDetails(usn);
        if (response != null) {
            if (!response.isEmpty()) {
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(422).body(noDataFoundException);
    }

    @GetMapping("/getallfeedata/sem/dept")
    public ResponseEntity getallfeedataSem(@RequestParam("sem") int sem, @RequestParam("deptName") String deptName) {
        List<StudentFee> response = feeService.getAllFeeData(sem, deptName);
        if (response != null) {
            if (!response.isEmpty()) {
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(422).body(noDataFoundException);
    }


    @GetMapping("/downloadfee/")
    public void downloadFile(@RequestParam(value = "id") Long id, HttpServletResponse response) throws IOException {

        FeeReceiptDAO responsefile = feeService.downloadFile(id);
        if (responsefile != null) {
            response.setHeader("Content-Disposition", "attachment; filename=" + responsefile.getFileName());
            response.getOutputStream().write(responsefile.getFileStream());
            response.getOutputStream().close();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
