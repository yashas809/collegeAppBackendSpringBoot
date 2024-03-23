package com.collegeManagement.app.controller;


import com.collegeManagement.app.dao.AttendanceDAO;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.service.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController
{
    @Autowired
    private IAttendanceService service;

    @Autowired
    private InputException inputException;

    @Autowired
    private NoDataFoundException noDataFoundException;

    @PostMapping("/add")
    public ResponseEntity addAttendace(@RequestBody AttendanceDAO requestBody)
    {
        AttendanceDAO response=service.create(requestBody);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(422).body(inputException);
        }
    }

    @GetMapping("/get/attendance")
    public ResponseEntity getAttendancebyUsnAndSem(@RequestParam(name = "sem") int sem, @RequestParam(name = "usn") String usn)
    {
        List<AttendanceDAO> response = service.getAttendanceByUSNAndSem(usn, sem);
        if(!response.isEmpty())
        {
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(421).body(noDataFoundException);
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestParam(name = "usn") String usn,@RequestParam(name = "sem") int sem, @RequestParam(name = "subject") String subjectName,
                                 @RequestBody AttendanceDAO requestBody)
    {
       AttendanceDAO response =service.update(usn,subjectName,sem,requestBody);
       if(response!=null)
       {
           return ResponseEntity.ok(response);
       }else{
           return ResponseEntity.status(422).body(inputException);
       }
    }

}
