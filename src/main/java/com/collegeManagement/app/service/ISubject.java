package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.SubjectDAO;

import java.util.List;

public interface ISubject
{
    public SubjectDAO createSubject(SubjectDAO requestData);

    public SubjectDAO updateSubject(String subjectCode, SubjectDAO requestData);

    public List<SubjectDAO> getSubjectsBySem(int Sem);

    public SubjectDAO getSubjectByName(String subjectName);

    public SubjectDAO getSubjectBySubjectCode(String subjectCode);

    public boolean deleteSubject(String subjectCode);
}
