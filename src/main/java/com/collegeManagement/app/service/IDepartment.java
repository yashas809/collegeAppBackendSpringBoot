package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Department;
import com.collegeManagement.app.entity.DepartmentEntity;

import java.util.List;

public interface IDepartment {

    public List<DepartmentEntity> getAll();

    public DepartmentEntity createDepartment(Department department);

    public List<DepartmentEntity> deletreDepartment(Department department);
}
