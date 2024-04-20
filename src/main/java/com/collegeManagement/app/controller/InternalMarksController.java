package com.collegeManagement.app.controller;

import com.collegeManagement.app.dao.InternalMarksDAO;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.service.IInternalMarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internalMarks")
@CrossOrigin(origins = "*")
public class InternalMarksController {
    @Autowired
    IInternalMarksService internalMarksService;

    @Autowired
    InputException inputException;

    @Autowired
    NoDataFoundException noDataFoundException;

    @GetMapping("/getInternalMarksforStudent")
    public ResponseEntity getInternalMarksforStudent(@RequestParam(value = "usn") String usn, @RequestParam(value = "sem") int sem) {
        List<InternalMarksDAO> response = internalMarksService.getInternalMarksforStudent(usn,sem);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @GetMapping("/getInternalMarks")
    public ResponseEntity getInternalMarksforStudent(@RequestParam(value = "usn") String usn, @RequestParam(value = "subject") String subjectName) {
        InternalMarksDAO response = internalMarksService.getInternalMarksforStudent(usn,subjectName);
        if (response!=null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }


    @GetMapping("/getInternalMarksByDept")
    public ResponseEntity getInternalMarksforStudent(@RequestParam(value = "sem") int sem, @RequestParam(value = "deptName") String deptName) {
        List<List<InternalMarksDAO>> response = internalMarksService.getInternalMarksforStudent(sem,deptName);
        if (response!=null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody InternalMarksDAO request) {
        InternalMarksDAO response = internalMarksService.add(request);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody InternalMarksDAO request, @RequestParam(value = "usn") String usn, @RequestParam(value = "subjectName") String subjectName) {
        InternalMarksDAO response = internalMarksService.update(request, usn, subjectName);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam(value = "usn") String usn, @RequestParam(value = "subjectName") String subjectName) {
        boolean response = internalMarksService.delete(usn, subjectName);
        if (response) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(422).body(inputException);
    }
}
