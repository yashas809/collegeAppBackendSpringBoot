package com.collegeManagement.app.controller;

import com.collegeManagement.app.dao.FeeStructure;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.service.IFeeStructure;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feeStructure")
public class FeeStructureController {

    @Autowired
    IFeeStructure feeStructureService;


    @Autowired
    InputException ioExp;

    @Autowired
    NoDataFoundException noDataFoundException;

    @PostMapping("/create")
    public ResponseEntity createFeeStructure(@RequestBody FeeStructure request)
    {
       FeeStructure response = feeStructureService.create(request);
       if(response!=null)
       {
            return ResponseEntity.ok(response);
       }

       return ResponseEntity.status(422).body(ioExp);
    }

    @GetMapping("/getFeeStructureDepartmentWise")
    public ResponseEntity getFeeStructureDepartmentWise(@RequestParam(value = "departmentName") String departmentName)
    {
        List<FeeStructure> response = feeStructureService.getFeeStructureDepartmentWise(departmentName);
        if(!response.isEmpty())
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @PutMapping("/updateFeeStructure")
    public ResponseEntity updateFeeStructure(@RequestParam(value = "departmentName") String departmentName, @RequestParam(value = "sem") int sem, @RequestParam(value = "feeamount") double feeamount)
    {
       boolean flag = feeStructureService.update(departmentName,sem,feeamount);
        if(flag)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(422).body(ioExp);
    }

    @DeleteMapping("/deleteFeeStructure")
    public ResponseEntity deleteFeeStructure(@RequestParam(value = "departmentName") String departmentName, @RequestParam(value = "sem") int sem)
    {
        boolean flag = feeStructureService.delete(departmentName,sem);
        if(flag)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(422).body(ioExp);
    }


}
