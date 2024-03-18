package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long>
{
    public Optional<List<AttendanceEntity>> findByUsnAndSem(String usn, int sem);
}
