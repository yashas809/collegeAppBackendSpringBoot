package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.SubjectDAO;
import com.collegeManagement.app.dao.TimeTableDAO;

import java.util.List;

public interface ITimeTableService
{
    public TimeTableDAO create(TimeTableDAO request);

    public TimeTableDAO update(TimeTableDAO request,long TimeTablePK);

    public List<TimeTableDAO> getTimeTableBySemAndDept(String departmentName, int sem);

    public boolean deleteTimeTable(long TimeTablePK);
}
