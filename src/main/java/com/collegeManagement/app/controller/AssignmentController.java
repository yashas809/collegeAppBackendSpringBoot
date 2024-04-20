package com.collegeManagement.app.controller;


import com.collegeManagement.app.dao.AssignmentDAO;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.service.IAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/assignment")
@CrossOrigin(origins = "*")
public class AssignmentController {

    @Autowired
    IAssignmentService service;

    @Autowired
    InputException inputException;

    @Autowired
    NoDataFoundException noDataFoundException;

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody AssignmentDAO request)
    {
       AssignmentDAO response = service.create(request);
       if(response!=null)
       {
           return ResponseEntity.ok(response);
       }else{
           return ResponseEntity.status(422).body(inputException);
       }
    }

    @GetMapping("/getbySubject")
    public ResponseEntity getBySubject(@RequestParam(name ="subjectName") String subjectName)
    {
        List<AssignmentDAO> response = service.getassignmentofSubject(subjectName);
        if(!response.isEmpty())
        {
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(421).body(noDataFoundException);
        }
    }

    @GetMapping("/getBySemAndDept")
    public ResponseEntity getBySubject(@RequestParam(name ="departmentName") String departmentName, @RequestParam(name = "sem") long sem)
    {
        List<AssignmentDAO> response = service.getassignmentBasedonSemAndDept(departmentName, sem);
        if(!response.isEmpty())
        {
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(421).body(noDataFoundException);
        }
    }

    @GetMapping("/getbyusn")
    public ResponseEntity getByusn(@RequestParam(name ="usn") String usn, @RequestParam(name="sem") long sem)
    {
        List<AssignmentDAO> response = service.getassignmentofStudent(usn,sem);
        if(!response.isEmpty())
        {
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(421).body(noDataFoundException);
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestParam(name ="usn") String usn, @RequestParam(name ="subjectName") String subjectName, @RequestParam(name = "isSubmitted") boolean isSubmitted)
    {
        AssignmentDAO response = service.update(isSubmitted,usn,subjectName);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(422).body(inputException);
        }
    }
}
