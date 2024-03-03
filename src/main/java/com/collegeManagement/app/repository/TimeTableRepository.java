package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.TimeTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeTableRepository extends JpaRepository<TimeTableEntity, Long>
{
    public Optional<List<TimeTableEntity>> findBydeptFKAndSem(long deptFK, int sem);
}
