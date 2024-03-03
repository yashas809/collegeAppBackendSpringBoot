package com.collegeManagement.app.controller;

import com.collegeManagement.app.dao.ExternalMarksDAO;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.service.IExternalMarksService;
import com.collegeManagement.app.service.IExternalMarksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/externalMarks")
public class ExternalMarksController {
    @Autowired
    IExternalMarksService externalMarksService;

    @Autowired
    InputException inputException;

    @Autowired
    NoDataFoundException noDataFoundException;

    @GetMapping("/getBySem")
    public ResponseEntity getBySem(@RequestParam(value = "sem") int sem) {
        List<ExternalMarksDAO> response = externalMarksService.getBySem(sem);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @GetMapping("/getByDept")
    public ResponseEntity getByDept(@RequestParam(value = "departmentName") String departmentName) {
        List<ExternalMarksDAO> response = externalMarksService.getByDept(departmentName);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @GetMapping("/getByUsnAndSem")
    public ResponseEntity getByUsnAndSem(@RequestParam(value = "usn") String usn, @RequestParam(value = "Sem") int sem) {
        List<ExternalMarksDAO> response = externalMarksService.getByUsnAndSem(usn, sem);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }


    @PostMapping("/add")
    public ResponseEntity addExternalMarks(@RequestBody List<ExternalMarksDAO> request) {
        List<ExternalMarksDAO> response = externalMarksService.add(request);
        if (response!=null) {
            if(!response.isEmpty())
            {
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(422).body(inputException);
    }

    @PutMapping("/update")
    public ResponseEntity updateExternalMarks(@RequestBody ExternalMarksDAO request, @RequestParam(value = "usn") String usn, @RequestParam(value = "Sem") int sem
            , @RequestParam(value = "subjectName") String SubjectName, @RequestParam(value = "departmentName") String departmentName) {
        ExternalMarksDAO response = externalMarksService.update(request, usn, SubjectName, departmentName, sem);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);
    }
}
