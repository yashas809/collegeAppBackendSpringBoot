package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>
{
    public List<DepartmentEntity> findAll();

    public Optional<DepartmentEntity> findBydepartmentName(String departmentName);
}
