package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Role;
import com.collegeManagement.app.entity.RoleEntity;

import java.util.List;

public interface IRole {

    public List<RoleEntity> getRoles();

    public RoleEntity createRole(Role roleData);

    public boolean deleteRole(String roleName);

}
