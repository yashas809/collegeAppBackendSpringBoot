package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.InternalMarksDAO;
import com.collegeManagement.app.entity.InternalMarksEntity;
import com.collegeManagement.app.entity.StudentEntity;
import com.collegeManagement.app.entity.SubjectEntity;
import com.collegeManagement.app.repository.InternalMarksRepository;
import com.collegeManagement.app.repository.StudentRepository;
import com.collegeManagement.app.repository.SubjectRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public InternalMarksDAO add(InternalMarksDAO request) {
        try {
            if (request != null) {
                if (request.getUsn() != null && request.getSubjectName() != null) {
                    Optional<StudentEntity> optionalStudentEntity = studentRepository.findByusn(request.getUsn());
                    Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(request.getSubjectName());
                    if (optionalStudentEntity.isPresent() && optionalSubjectEntity.isPresent()) {
                        InternalMarksEntity internalMarksEntity = InternalMarksEntity.build(0l, request.getUsn(), optionalSubjectEntity.get().getSubjectPK(),
                                request.getMaxMarks(), request.getMarksScored());
                        internalMarksEntity = internalMarksRepository.save(internalMarksEntity);
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
