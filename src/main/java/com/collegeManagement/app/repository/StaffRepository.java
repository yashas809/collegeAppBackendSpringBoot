package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffEntity, Long>
{
    StaffEntity findByName(String name);

    StaffEntity findByloginfk(long loginfk);
}
