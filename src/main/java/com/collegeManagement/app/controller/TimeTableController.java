package com.collegeManagement.app.controller;

import com.collegeManagement.app.dao.TimeTableDAO;
import com.collegeManagement.app.exception.InputException;
import com.collegeManagement.app.exception.NoDataFoundException;
import com.collegeManagement.app.service.ITimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetable")
public class TimeTableController {

    @Autowired
    ITimeTableService timeTableService;

    @Autowired
    NoDataFoundException noDataFoundException;

    @Autowired
    InputException inputException;

    @GetMapping("/getBySemAndDept")
    public ResponseEntity getBySemDept(@RequestParam(value = "sem") int sem, @RequestParam(value = "departmentName") String departmentName) {
        List<TimeTableDAO> response = timeTableService.getTimeTableBySemAndDept(departmentName, sem);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(421).body(noDataFoundException);
    }

    @PostMapping("/create")
    public ResponseEntity createTimeTable(@RequestBody TimeTableDAO request) {
        TimeTableDAO response = timeTableService.create(request);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);
    }

    @PutMapping("/update")
    public ResponseEntity UpdateTimeTable(@RequestBody TimeTableDAO request, @RequestParam(value = "id") long id) {
        TimeTableDAO response = timeTableService.update(request,id);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(422).body(inputException);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTimeTable(@RequestParam(value = "id") long id)
    {
        if(timeTableService.deleteTimeTable(id))
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(422).body(inputException);
    }
}
