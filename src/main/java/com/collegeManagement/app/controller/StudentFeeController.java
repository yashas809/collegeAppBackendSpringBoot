package com.collegeManagement.app.controller;


import com.collegeManagement.app.dao.StudentFee;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.service.IStudentFeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/student/fee")
public class StudentFeeController
{

    @Autowired
    IStudentFeeService feeService;

    @Autowired
    InputException ioexp;

    @PostMapping("/add")
    public ResponseEntity addFee(@RequestPart("data") StudentFee request, @RequestPart("file")MultipartFile file)
    {
        if(!file.isEmpty() )
        {
            if(file.getContentType().equals("application/pdf"))
            {
                try{

                    request.setFileData(file.getInputStream().readAllBytes());
                    request.setFileName(file.getOriginalFilename());
                    System.out.println(file.getContentType());
                    StudentFee response = feeService.createEntry(request);
                    if(response!=null)
                    {
                        return ResponseEntity.ok(response);
                    }

                }catch (Exception exp)
                {
                    exp.printStackTrace();
                }

            }else {
                return ResponseEntity.status(402).body("Only PDF's are allowed");
            }

            }
        return ResponseEntity.status(422).body(ioexp);

    }

    @GetMapping("/downloadfee/")
    public void downloadFile(@RequestParam(value = "id") Long id, HttpServletResponse response) throws IOException {

        byte[] file = feeService.downloadFile(id);
        if (file != null) {
            response.setHeader("Content-Disposition", "attachment; filename=" + "yashas");
            response.getOutputStream().write(file);
            response.getOutputStream().close();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
