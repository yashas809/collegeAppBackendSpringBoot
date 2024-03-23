package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.AssignmentDAO;

import java.util.List;

public interface IAssignmentService
{
    public AssignmentDAO create(AssignmentDAO request);

    public List<AssignmentDAO> getassignmentofStudent(String usn);

    public List<AssignmentDAO> getassignmentofSubject(String subjectName);

    public AssignmentDAO update(boolean isSubmitted, String usn, String subjectName);

}
