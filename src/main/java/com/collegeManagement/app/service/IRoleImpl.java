package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Role;
import com.collegeManagement.app.entity.RoleEntity;
import com.collegeManagement.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IRoleImpl implements IRole {

    @Autowired
    private RoleRepository repository;


    @Override
    public List<RoleEntity> getRoles() {
        return repository.findAll();
    }

    @Override
    public RoleEntity createRole(Role roleData) {
        try
        {
            RoleEntity entityData = RoleEntity.build(0l, roleData.getRolename());
            entityData = repository.save(entityData);
            return entityData;

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean deleteRole(String roleName) {

        if(roleName!=null)
        {
            RoleEntity entityData = repository.findByroleName(roleName);
            if(entityData!=null)
            {
                repository.deleteById(entityData.getRolePK());
                return true;
            }

        }
        return false;
    }

}
