package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Login;
import com.collegeManagement.app.entity.LoginEntity;
import com.collegeManagement.app.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import Password.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ILoginImpl implements ILogin{

    @Autowired
    LoginRepository repository;

    @Override
    public LoginEntity createLogin(Login logindata) {

        try
        {
            PasswordClass utlityClass = Password.Utility.EncryptPassword(logindata.getPassword());
            logindata.setSecretKey(utlityClass.getSalt());
            logindata.setPassword(utlityClass.getEncryptedPassword());
            LoginEntity entityData = LoginEntity.build(0l,logindata.getLoginName(), logindata.getPassword(), LocalDateTime.now(),false, logindata.getSecretKey());
            entityData = repository.save(entityData);
            return entityData;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public LoginEntity findByloginPK(Long loginPk) {

        try
        {
            LoginEntity entityData = repository.findByloginPK(loginPk);
            return entityData;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LoginEntity findByloginName(String loginName) {

        try{
            LoginEntity entityData = repository.findByloginName(loginName);
            return entityData;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean verifyPassword(String enteredPassword, String storedPassword, String salt) {

        try
        {
            boolean flag = Password.Utility.Verify(enteredPassword,salt,storedPassword);
            return flag;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
}
