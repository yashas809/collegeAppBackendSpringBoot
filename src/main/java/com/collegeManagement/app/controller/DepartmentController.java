package com.collegeManagement.app.controller;


import com.collegeManagement.app.dao.Department;
import com.collegeManagement.app.entity.DepartmentEntity;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.service.IDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/department")
@CrossOrigin(origins = "*")
public class DepartmentController
{
        @Autowired
        IDepartment department;

        @Autowired
        InputException exception;

        @GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<DepartmentEntity>> getAll()
        {
            return ResponseEntity.ok(department.getAll());
        }

        @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity create( @RequestBody Department request)
        {
            DepartmentEntity response = department.createDepartment(request);
            if(response!=null)
            {
                return ResponseEntity.ok(response);
            }
            else
            {
                return ResponseEntity.status(422).body(exception);
            }

        }

}
