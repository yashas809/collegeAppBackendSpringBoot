package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.StudentFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentFeeRepository extends JpaRepository<StudentFeeEntity, Long> {

    public Optional<List<StudentFeeEntity>> findBystudentFK(long studentFK);

}
