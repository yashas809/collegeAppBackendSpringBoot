package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.FeeStructure;

import java.util.List;

public interface IFeeStructure {

    public FeeStructure create(FeeStructure request);

    public List<FeeStructure> getFeeStructureDepartmentWise(String departmentName);

    public boolean update(String departmentName, int sem, double fee);

    public boolean delete(String departmentName, int sem);

}
