package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Department;
import com.collegeManagement.app.entity.DepartmentEntity;
import com.collegeManagement.app.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IDepartmentImpl implements IDepartment
{

    @Autowired
    DepartmentRepository repository;

    @Override
    public List<DepartmentEntity> getAll() {
       return repository.findAll();
    }

    @Override
    public DepartmentEntity createDepartment(Department department) {
        try
        {
            DepartmentEntity entityData = DepartmentEntity.build(0l, department.getDepartmentName());
            entityData = repository.save(entityData);
            return entityData;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DepartmentEntity> deletreDepartment(Department department) {
        return null;
    }
}
