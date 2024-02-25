package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Admin;
import com.collegeManagement.app.entity.AdminEntity;

import java.util.List;

public interface IAdmin
{
    public AdminEntity create(Admin request);

    public List<Admin> getAll();

    public Admin VerifyLogin(String userName, String password);


}
