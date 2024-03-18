package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.AttendanceDAO;

import java.util.List;

public interface IAttendanceService
{
    public AttendanceDAO create(AttendanceDAO request);

    public List<AttendanceDAO> getAttendanceByUSNAndSem(String usn, int sem);

    public AttendanceDAO update(String usn, String subjectName ,int sem, AttendanceDAO request);

    public boolean delete(String usn, int sem, String subjectName);
}
