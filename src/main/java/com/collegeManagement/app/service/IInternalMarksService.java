package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.InternalMarksDAO;

import java.util.List;

public interface IInternalMarksService
{
    public InternalMarksDAO add(InternalMarksDAO request);

    public List<InternalMarksDAO> getInternalMarksforStudent(String usn);

    public List<List<InternalMarksDAO>> getInternalMarksforStudent(int sem, String deptName);

    public List<InternalMarksDAO> getInternalMarksforStudent(String usn, int sem);

    public InternalMarksDAO getInternalMarksforStudent(String usn, String subjectName);

    public InternalMarksDAO update(InternalMarksDAO request, String usn, String subjectName);

    public boolean delete(String usn, String subjectName);
}
