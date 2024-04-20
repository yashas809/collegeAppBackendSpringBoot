package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.FeeReceiptDAO;
import com.collegeManagement.app.dao.StudentFee;

import java.util.List;

public interface IStudentFeeService {

    public StudentFee createEntry(StudentFee request);

    public List<StudentFee> getStudentFeeDetails(String usn);

    public List<StudentFee> getStudentFeeDetailsbySem(int sem);
    public List<StudentFee> getAllFeeData(int sem, String deptName);

    public FeeReceiptDAO downloadFile(long id);
}
