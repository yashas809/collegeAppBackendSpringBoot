package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Login;
import com.collegeManagement.app.entity.LoginEntity;

public interface ILogin {

    public LoginEntity createLogin(Login logindata);

    public LoginEntity updatePassword(String loginName,String password);

    public LoginEntity findByloginPK(Long loginPk);

    public LoginEntity findByloginName(String loginName);

    public boolean verifyPassword(String enteredPassword, String storedPassword, String salt);
}
