package com.collegeManagement.app.controller;

import com.collegeManagement.app.entity.LoginEntity;
import com.collegeManagement.app.service.ILogin;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@CrossOrigin(origins = "*")
public class PasswordController {

    @Autowired
    private ILogin iLogin;

    @PutMapping("/reset")
    public ResponseEntity resetPassword(@RequestParam(name = "loginName") String loginName, @RequestParam(name = "password") String password) {
       LoginEntity loginEntity = iLogin.updatePassword(loginName,password);
       if(loginEntity!=null)
       {
           return  ResponseEntity.ok().build();
       }else{
           return ResponseEntity.status(422).build();
       }
    }
}
