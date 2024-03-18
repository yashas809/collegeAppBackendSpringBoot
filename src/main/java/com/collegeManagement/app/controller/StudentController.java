package com.collegeManagement.app.controller;


import com.collegeManagement.app.dao.Student;
import com.collegeManagement.app.entity.StudentEntity;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.InvalidPasswordException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.service.IStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentController
{
    @Autowired
    IStudent studentService;

    @Autowired
    InputException inputException;

    @Autowired
    NoDataFoundException noDataFoundException;

    @Autowired
    InvalidPasswordException pwdException;

    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody Student requestbody)
    {
        Student response = studentService.createStudent(requestbody);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);
    }

    @GetMapping("/getBySem")
    public ResponseEntity<?> getBySem(@RequestParam(value = "sem") int sem)
    {
        List<Student> response = studentService.getAllStudentsBasedOnSem(sem);
        if(!response.isEmpty())
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @GetMapping("/getByUSN")
    public ResponseEntity<?> getBySem(@RequestParam(value = "usn") String usn)
    {
        Student response = studentService.GetStudent(usn);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getBySem()
    {
        List<Student> response = studentService.getAllStudents();
        if(!response.isEmpty())
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateStudentInfo(@RequestParam(value = "usn") String usn, @RequestBody Student request)
    {
        Student response = studentService.updateStudent(usn,request);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);

    }

    @GetMapping("/login")
    public ResponseEntity getLogin(@RequestParam String loginName, @RequestParam String password)
    {
        Student response = studentService.login(loginName,password);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(pwdException);
    }

}
