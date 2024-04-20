package com.collegeManagement.app.controller;

import com.collegeManagement.app.dao.Staff;
import com.collegeManagement.app.entity.StaffEntity;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.InvalidPasswordException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
@CrossOrigin(origins = "*")
public class StaffController {

    @Autowired
    IStaffService service;

    @Autowired
    InputException exception;

    @Autowired
    InvalidPasswordException pwdException;

    @Autowired
    NoDataFoundException noDataFoundException;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody Staff request)
    {
        StaffEntity response = service.createStaff(request);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }else
        {
            return ResponseEntity.status(422).body(exception);
        }
    }

    @GetMapping(value = "/login")
    public ResponseEntity login(@RequestParam @NotBlank String userName, @RequestParam @NotBlank String password)
    {
        Staff staff = service.StaffLogin(userName,password);

        if(staff!=null)
        {
            return ResponseEntity.ok(staff);
        }else{
            return ResponseEntity.status(422).body(pwdException);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity update(@RequestParam @NotBlank long staffId, @RequestBody Staff request)
    {
        Staff staff = service.updateStaff(staffId,request);
        if(staff!=null)
        {
            return ResponseEntity.ok(staff);
        }
        return ResponseEntity.status(422).body(exception);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity getAll()
    {
        List<Staff> staff = service.getAll();

        if(!staff.isEmpty())
        {
            return ResponseEntity.ok(staff);
        }else{
            return ResponseEntity.status(421).body(noDataFoundException);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam(name = "staffId") long staffId)
    {
        boolean flag = service.delete(staffId);
        if(flag)
        {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(421).body(noDataFoundException);
        }
    }

}
