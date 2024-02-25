package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    AdminEntity findByloginFK(long loginFK);

}
