package com.collegeManagement.app.controller;

import com.collegeManagement.app.dao.Admin;
import com.collegeManagement.app.entity.AdminEntity;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.InvalidPasswordException;
import com.collegeManagement.app.service.IAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController
{

    @Autowired
    InputException exception;

    @Autowired
    InvalidPasswordException passwordException;

    @Autowired
    IAdmin admin;
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Admin request)
    {
        AdminEntity response = admin.create(request);
        if(response!=null)
        {
            return ResponseEntity.ok(response);
        }else
        {
            return ResponseEntity.status(422).body(exception);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Admin>> getAll()
    {
        return ResponseEntity.ok(admin.getAll());
    }

    @GetMapping("/login")
    public ResponseEntity loginVerification(@RequestParam String userName, @RequestParam String password)
    {
       Admin response = admin.VerifyLogin(userName,password);
       if(response!=null)
       {
           return ResponseEntity.ok().body(response);
       }
       return ResponseEntity.badRequest().body(passwordException);
    }

}
