package com.collegeManagement.app.controller;

import com.collegeManagement.app.dao.SubjectDAO;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.service.ISubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@CrossOrigin(origins = "*")
public class SubjectController {
    @Autowired
    ISubject subjectService;

    @Autowired
    NoDataFoundException noDataFoundException;

    @Autowired
    InputException inputException;

    @GetMapping("/getbysem")
    public ResponseEntity getBySem(@RequestParam(value = "sem") int sem) {
        List<SubjectDAO> response = subjectService.getSubjectsBySem(sem);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @GetMapping("/getSubjectByName")
    public ResponseEntity getSubjectByName(@RequestParam(value = "SubjectName") String SubjectName) {
        SubjectDAO response = subjectService.getSubjectByName(SubjectName);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @GetMapping("/getSubjectBySubjectCode")
    public ResponseEntity getSubjectBySubjectCode(@RequestParam(value = "SubjectCode") String subjectCode) {
        SubjectDAO response = subjectService.getSubjectBySubjectCode(subjectCode);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @PostMapping("/add")
    public ResponseEntity createSubject(@RequestBody SubjectDAO requestData) {
        SubjectDAO response = subjectService.createSubject(requestData);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);
    }

    @PutMapping("/update")
    public ResponseEntity updateSubject(@RequestBody SubjectDAO requestData, @RequestParam(value = "subjectCode") String subjectCode) {
        SubjectDAO response = subjectService.updateSubject(subjectCode, requestData);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteSubject(@RequestParam(value = "subjectCode") String subjectCode) {
        boolean response = subjectService.deleteSubject(subjectCode);
        if (response) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);
    }
}
