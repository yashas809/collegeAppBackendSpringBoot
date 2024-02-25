package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Admin;
import com.collegeManagement.app.dao.Login;
import com.collegeManagement.app.entity.AdminEntity;
import com.collegeManagement.app.entity.LoginEntity;
import com.collegeManagement.app.entity.RoleEntity;
import com.collegeManagement.app.repository.AdminRepository;
import com.collegeManagement.app.repository.LoginRepository;
import com.collegeManagement.app.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IAdminImpl implements IAdmin
{

    @Autowired
    AdminRepository repository;

    @Autowired
    ILogin loginrepository;

    @Autowired
    Login loginData;

    @Autowired
    RoleRepository roleRepository;

    RoleEntity roleentityData;

    AdminEntity entityData;

    LoginEntity loginentity;

    @Override
    @Transactional
    public AdminEntity create(Admin request) {

        try{
            if(request!=null)
            {
                loginData.setPassword(request.getPassword());
                loginData.setLoginName(request.getLoginName());
                LoginEntity loginEntity = loginrepository.createLogin(loginData);

                if(request.getRoleName()!=null)
                {
                    roleentityData = roleRepository.findByroleName(request.getRoleName());
                }

                if(loginEntity!=null && roleentityData.getRolePK()!=0l)
                {
                    entityData = AdminEntity.build(0l,request.getUsername(),loginEntity.getLoginPK(),roleentityData.getRolePK());
                    entityData = repository.save(entityData);
                }
            }
                return entityData;
        }catch (Exception e)
        {
            e.printStackTrace();
            return entityData;
        }


    }

    @Override
    public List<Admin> getAll() {
        List<AdminEntity> entityData = repository.findAll();
        List<Admin> adminData = new ArrayList<Admin>();

        for(AdminEntity i : entityData)
        {
            if(i.getRoleFK()!=0l)
            {
                roleentityData = roleRepository.findByrolePK(i.getRoleFK());
            }

            if(i.getLoginFK()!=0l)
            {
                loginentity = loginrepository.findByloginPK(i.getLoginFK());
            }
            Admin data = Admin.build(i.getName(), roleentityData.getRoleName(), "", loginentity.getLoginName());
            adminData.add(data);
        }
        return adminData;
    }

    @Override
    public Admin VerifyLogin(String userName, String password) {
        try
        {

            loginentity = loginrepository.findByloginName(userName);
            if(loginentity!=null)
            {
              boolean flag =  loginrepository.verifyPassword(password,loginentity.getPassword(),loginentity.getSecretKey());
              if(flag)
              {
                  Admin data = new Admin();
                  entityData = repository.findByloginFK(loginentity.getLoginPK());
                  if(entityData!=null)
                  {
                      if(entityData.getRoleFK()!=0l)
                      {
                          roleentityData = roleRepository.findByrolePK(entityData.getRoleFK());
                      }


                      data = Admin.build(entityData.getName(),roleentityData.getRoleName(),"", userName);
                      return data;
                  }
              }
            }
            return null;

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
