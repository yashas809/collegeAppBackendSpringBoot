package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>
{
    List<RoleEntity> findAll();
    RoleEntity findByroleName(String roleName);

    RoleEntity findByrolePK(long rolePK);
}
