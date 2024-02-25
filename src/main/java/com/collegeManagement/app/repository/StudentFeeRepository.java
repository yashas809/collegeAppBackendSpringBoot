package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.StudentFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentFeeRepository extends JpaRepository<StudentFeeEntity, Long> {
}
