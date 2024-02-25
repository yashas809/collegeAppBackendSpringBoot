package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.LoginEntity;
import com.collegeManagement.app.entity.RoleEntity;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginEntity, Long> {
    LoginEntity findByloginPK(long loginPK);

    LoginEntity findByloginName(String loginName);
}
