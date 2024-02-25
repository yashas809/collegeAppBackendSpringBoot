package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Student;
import com.collegeManagement.app.entity.StudentEntity;

import java.util.List;

public interface IStudent
{
    public Student createStudent(Student requestData);

    public Student GetStudent(String usn);

    public Student GetStudentSemSummary(String usn);

    public List<Student> getAllStudentsBasedOnSem(int sem);

    public List<Student> getAllStudents();

    public Student updateStudent(String usn, Student requestData);

    public Student login(String userName, String password);

}
