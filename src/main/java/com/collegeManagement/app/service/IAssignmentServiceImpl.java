package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.AssignmentDAO;
import com.collegeManagement.app.entity.AssignmentEntity;
import com.collegeManagement.app.entity.StudentEntity;
import com.collegeManagement.app.entity.SubjectEntity;
import com.collegeManagement.app.repository.AssignmentRepository;
import com.collegeManagement.app.repository.StudentRepository;
import com.collegeManagement.app.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IAssignmentServiceImpl implements IAssignmentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Override
    public AssignmentDAO create(AssignmentDAO request) {
        if (request != null) {
            Optional<StudentEntity> studentEntityOptional = studentRepository.findByusn(request.getUsn());
            Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(request.getSubjectName());
            if (studentEntityOptional.isPresent() && optionalSubjectEntity.isPresent()) {
                SubjectEntity subjectEntityData = optionalSubjectEntity.get();
                if(assignmentRepository.findByUsnAndSubjectFK(request.getUsn(), subjectEntityData.getSubjectPK()).isPresent())
                {
                    return null;
                }
                AssignmentEntity entityData = AssignmentEntity.build(0L, subjectEntityData.getSubjectPK(), request.getUsn(), request.getIsAssignmentSubmitted());
                assignmentRepository.save(entityData);
                return request;
            }
        }
        return null;
    }

    @Override
    public List<AssignmentDAO> getassignmentofStudent(String usn) {
        Optional<List<AssignmentEntity>> listOptionalAssignmentEntity = assignmentRepository.findByusn(usn);
        if (listOptionalAssignmentEntity.isPresent()) {
            List<AssignmentEntity> entityList = listOptionalAssignmentEntity.get();
            List<AssignmentDAO> response = new ArrayList<AssignmentDAO>();
            for (AssignmentEntity data : entityList) {
                response.add(AssignmentDAO.build(subjectRepository.findById(data.getSubjectFK()).get().getSubjectName(), usn, data.isSubmitted()));
            }
            return response;
        }
        return null;
    }

    @Override
    public List<AssignmentDAO> getassignmentofSubject(String subjectName) {
        Optional<List<AssignmentEntity>> listOptionalAssignmentEntity = assignmentRepository.findBysubjectFK(subjectRepository.findBysubjectName(subjectName).get().getSubjectPK());
        if (listOptionalAssignmentEntity.isPresent()) {
            List<AssignmentEntity> entityList = listOptionalAssignmentEntity.get();
            List<AssignmentDAO> response = new ArrayList<AssignmentDAO>();
            for (AssignmentEntity data : entityList) {
                response.add(AssignmentDAO.build(subjectName, data.getUsn(), data.isSubmitted()));
            }
            return response;
        }
        return null;
    }

    @Transactional
    @Override
    public List<AssignmentDAO> getassignmentBasedonSemAndDept(String departmentName, long sem) {
        Optional<List<AssignmentEntity>> listOptionalAssignmentEntity = assignmentRepository.uspgetAssignment(departmentName,sem);
        if (listOptionalAssignmentEntity.isPresent()) {
            List<AssignmentEntity> entityList = listOptionalAssignmentEntity.get();
            List<AssignmentDAO> response = new ArrayList<AssignmentDAO>();
            for (AssignmentEntity data : entityList) {
                response.add(AssignmentDAO.build(subjectRepository.findById(data.getSubjectFK()).get().getSubjectName(), data.getUsn(), data.isSubmitted()));
            }
            return response;
        }
        return null;
    }

    @Transactional
    @Override
    public List<AssignmentDAO> getassignmentofStudent(String usn, long sem) {
        Optional<List<AssignmentEntity>> listOptionalAssignmentEntity = assignmentRepository.uspgetAssignmentByUSNandSem(usn, sem);
        if (listOptionalAssignmentEntity.isPresent()) {
            List<AssignmentEntity> entityList = listOptionalAssignmentEntity.get();
            List<AssignmentDAO> response = new ArrayList<AssignmentDAO>();
            for (AssignmentEntity data : entityList) {
                response.add(AssignmentDAO.build(subjectRepository.findById(data.getSubjectFK()).get().getSubjectName(), data.getUsn(), data.isSubmitted()));
            }
            return response;
        }
        return null;
    }

    @Override
    public AssignmentDAO update(boolean isSubmitted, String usn, String subjectName) {
        Optional<AssignmentEntity> optionalAssignmentEntity = assignmentRepository.findByUsnAndSubjectFK(usn, subjectRepository.findBysubjectName(subjectName).get().getSubjectPK());
        if (optionalAssignmentEntity.isPresent()) {
            AssignmentEntity assignmentEntity = optionalAssignmentEntity.get();
            assignmentEntity.setSubmitted(isSubmitted);
            assignmentRepository.saveAndFlush(assignmentEntity);
            return AssignmentDAO.build(subjectName, usn, isSubmitted);
        }
        return null;
    }
}
