package com.collegeManagement.app.service;


import com.collegeManagement.app.dao.ExternalMarksDAO;

import java.util.List;

public interface IExternalMarksService {

    public List<ExternalMarksDAO> add(List<ExternalMarksDAO> request);

    public ExternalMarksDAO update(ExternalMarksDAO request, String usn, String subjectName, String departmentName, int sem);

    public List<ExternalMarksDAO> getByUsnAndSem(String usn, int sem);

    public List<ExternalMarksDAO> getBySem(int Sem);

    public List<ExternalMarksDAO> getByDept(String departmentName);
}
