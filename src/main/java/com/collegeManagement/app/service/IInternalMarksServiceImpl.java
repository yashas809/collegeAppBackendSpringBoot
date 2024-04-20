package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.AttendanceDAO;
import com.collegeManagement.app.dao.InternalMarksDAO;
import com.collegeManagement.app.entity.InternalMarksEntity;
import com.collegeManagement.app.entity.StudentEntity;
import com.collegeManagement.app.entity.SubjectEntity;
import com.collegeManagement.app.repository.DepartmentRepository;
import com.collegeManagement.app.repository.InternalMarksRepository;
import com.collegeManagement.app.repository.StudentRepository;
import com.collegeManagement.app.repository.SubjectRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IInternalMarksServiceImpl implements IInternalMarksService {

    @Autowired
    InternalMarksRepository internalMarksRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    DepartmentRepository deptRepo;

    @Override
    public InternalMarksDAO add(InternalMarksDAO request) {
        try {
            if (request != null) {
                if (request.getUsn() != null && request.getSubjectName() != null) {
                    Optional<StudentEntity> optionalStudentEntity = studentRepository.findByusn(request.getUsn());
                    Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(request.getSubjectName());
                    Optional<InternalMarksEntity> optionalInternalMarksEntity = internalMarksRepository.findByusnAndSubjectFK(request.getUsn(),optionalSubjectEntity.get().getSubjectPK());
                    if (optionalStudentEntity.isPresent() && optionalSubjectEntity.isPresent() && optionalInternalMarksEntity.isEmpty()) {
                        InternalMarksEntity internalMarksEntity = InternalMarksEntity.build(0l, request.getUsn(), optionalSubjectEntity.get().getSubjectPK(),
                                request.getMaxMarks(), request.getMarksScored());
                        internalMarksRepository.save(internalMarksEntity);
                        return request;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<InternalMarksDAO> getInternalMarksforStudent(String usn) {
        try {
            if (usn != null) {
                Optional<List<InternalMarksEntity>> optionalInternalMarksEntity = internalMarksRepository.findByusn(usn);
                if (optionalInternalMarksEntity.isPresent()) {
                    List<InternalMarksEntity> listOfInternalMarks = optionalInternalMarksEntity.get();
                    List<InternalMarksDAO> response = new ArrayList<InternalMarksDAO>();
                    for (InternalMarksEntity internalMarksEntity : listOfInternalMarks) {
                        response.add(InternalMarksDAO.build(usn, subjectRepository.findById(internalMarksEntity.getSubjectFK()).get().getSubjectName(),
                                internalMarksEntity.getMaximumMarks(), internalMarksEntity.getMarksScored()));
                    }
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public List<List<InternalMarksDAO>> getInternalMarksforStudent(int sem, String deptName) {
        Optional<List<StudentEntity>> optionalListStudentEntity = studentRepository.findBySemAndDeptfk(sem,deptRepo.findBydepartmentName(deptName).get().getId());
        if(optionalListStudentEntity.isPresent())
        {
            List<List<InternalMarksDAO>> response = new ArrayList<>();
            List<StudentEntity> listofStudentEntity = optionalListStudentEntity.get();
            boolean flag = false;
            for(StudentEntity studentEntity: listofStudentEntity)
            {
               List<InternalMarksDAO> internalMarksDAOList =   getInternalMarksforStudent(studentEntity.getUsn(),sem);
                if(!internalMarksDAOList.isEmpty())
                {
                    response.add(internalMarksDAOList);
                    flag = true;
                }
            }
            if(flag){
                return response;
            }
            return null;
        }
        return null;
    }

    @Transactional
    @Override
    public List<InternalMarksDAO> getInternalMarksforStudent(String usn, int sem) {
        try {
            if (usn != null) {
                Optional<List<InternalMarksEntity>> optionalInternalMarksEntity = internalMarksRepository.uspgetinternalmarksbyusnAndSem(sem,usn);
                if (optionalInternalMarksEntity.isPresent()) {
                    List<InternalMarksEntity> listOfInternalMarks = optionalInternalMarksEntity.get();
                    List<InternalMarksDAO> response = new ArrayList<InternalMarksDAO>();
                    for (InternalMarksEntity internalMarksEntity : listOfInternalMarks) {
                        response.add(InternalMarksDAO.build(usn, subjectRepository.findById(internalMarksEntity.getSubjectFK()).get().getSubjectName(),
                                internalMarksEntity.getMaximumMarks(), internalMarksEntity.getMarksScored()));
                    }
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InternalMarksDAO getInternalMarksforStudent(String usn, String subjectName) {
        try {
            if (usn != null) {
                Optional<InternalMarksEntity> optionalInternalMarksEntity = internalMarksRepository.findByusnAndSubjectFK(usn,subjectRepository.findBysubjectName(subjectName).get().getSubjectPK());
                if (optionalInternalMarksEntity.isPresent()) {
                    InternalMarksEntity listOfInternalMarks = optionalInternalMarksEntity.get();
                    return InternalMarksDAO.build(usn,subjectName,listOfInternalMarks.getMaximumMarks(), listOfInternalMarks.getMarksScored());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InternalMarksDAO update(InternalMarksDAO request, String usn, String subjectName) {
        try {
            if (usn != null && subjectName != null) {
                Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(subjectName);
                if (optionalSubjectEntity.isPresent()) {
                    Optional<InternalMarksEntity> OptionalinternalMarksEntity = internalMarksRepository.findByusnAndSubjectFK(usn, optionalSubjectEntity.get().getSubjectPK());
                    if (OptionalinternalMarksEntity.isPresent()) {
                        InternalMarksEntity internalMarksEntity = OptionalinternalMarksEntity.get();
                        if (request.getMarksScored() != 0d) {
                            internalMarksEntity.setMarksScored(request.getMarksScored());
                        }
                        internalMarksEntity = internalMarksRepository.saveAndFlush(internalMarksEntity);
                        return InternalMarksDAO.build(usn, subjectName, internalMarksEntity.getMaximumMarks(), internalMarksEntity.getMarksScored());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(String usn, String subjectName) {
        try {
            if (usn != null && subjectName != null) {
                Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(subjectName);
                if (optionalSubjectEntity.isPresent()) {
                    Optional<InternalMarksEntity> OptionalinternalMarksEntity = internalMarksRepository.findByusnAndSubjectFK(usn, optionalSubjectEntity.get().getSubjectPK());
                    if (OptionalinternalMarksEntity.isPresent()) {
                        internalMarksRepository.delete(OptionalinternalMarksEntity.get());
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
