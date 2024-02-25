package com.collegeManagement.app.controller;


import com.collegeManagement.app.dao.Role;
import com.collegeManagement.app.entity.RoleEntity;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.service.IRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRole irole;

    @Autowired
    InputException exc;

    @GetMapping("/get/allroles")
    public ResponseEntity<List<RoleEntity>> getRoles()
    {
        return ResponseEntity.ok(irole.getRoles());
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createRole(@RequestBody Role requestData) {

        RoleEntity response = irole.createRole(requestData);
        if(response!=null)
        {
           return ResponseEntity.ok(response);
        }
        else{

            return ResponseEntity.status(422).body(exc);

        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteRoles(@RequestParam String roleName)
    {
            boolean flag = irole.deleteRole(roleName);
            if(flag)
            {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(422).body(exc);
    }
}
