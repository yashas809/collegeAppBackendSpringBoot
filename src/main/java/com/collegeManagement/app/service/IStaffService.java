package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Staff;
import com.collegeManagement.app.entity.StaffEntity;

import java.util.List;

public interface IStaffService {

    public StaffEntity createStaff(Staff request);

    public List<Staff> getAll();

    public Staff getStaffByName(String staffName);

    public Staff updateStaff(long StaffId, Staff request);

    public boolean delete(long StaffId);

    public Staff StaffLogin(String userName, String password);

}
