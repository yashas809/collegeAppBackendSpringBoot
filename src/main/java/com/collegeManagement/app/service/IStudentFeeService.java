package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.StudentFee;

import java.util.List;

public interface IStudentFeeService {

    public StudentFee createEntry(StudentFee request);

    public List<StudentFee> getStudentFeeDetails(String usn);

    public List<StudentFee> getAllFeeData();

    public byte[] downloadFile(long id);
}
