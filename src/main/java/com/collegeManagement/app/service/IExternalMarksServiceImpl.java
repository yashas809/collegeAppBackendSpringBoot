package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.ExternalMarksDAO;
import com.collegeManagement.app.entity.*;
import com.collegeManagement.app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IExternalMarksServiceImpl implements IExternalMarksService {


    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    InternalMarksRepository internalMarksRepository;

    @Autowired
    ExternalMarksRepository externalMarksRepository;

    @Transactional
    @Override
    public List<ExternalMarksDAO> add(List<ExternalMarksDAO> request) {
        try {
            if (!request.isEmpty()) {
                Optional<List<ExternalMarksEntity>> DuplicateCheck = externalMarksRepository.findByUsnAndSem(request.get(0).getUsn(),request.get(0).getSem());
                if(DuplicateCheck.isPresent())
                {
                    List<ExternalMarksEntity> listofmarksDupCheck = DuplicateCheck.get();
                    if(!listofmarksDupCheck.isEmpty())
                    {
                        return null;
                    }
                }
                List<ExternalMarksEntity> listofExternalMarksEntity = new ArrayList<ExternalMarksEntity>();
                for (ExternalMarksDAO externalMarksDAO : request) {
                    if (externalMarksDAO.getDepartmentName() != null && externalMarksDAO.getSubjectName() != null && externalMarksDAO.getUsn() != null) {
                        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findBydepartmentName(externalMarksDAO.getDepartmentName());
                        Optional<StudentEntity> optionalStudentEntity = studentRepository.findByusn(externalMarksDAO.getUsn());
                        Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(externalMarksDAO.getSubjectName());
                        if (optionalSubjectEntity.isPresent() && optionalDepartmentEntity.isPresent() && optionalStudentEntity.isPresent()) {
                            Optional<InternalMarksEntity> optionalInternalMarksEntity = internalMarksRepository.findByusnAndSubjectFK(externalMarksDAO.getUsn(), optionalSubjectEntity.get().getSubjectPK());
                            if (optionalInternalMarksEntity.isPresent()) {
                                listofExternalMarksEntity.add(ExternalMarksEntity.build(0l, externalMarksDAO.getUsn(), optionalSubjectEntity.get().getSubjectPK(), optionalInternalMarksEntity.get().getInternalMarksPK(),
                                        externalMarksDAO.getExternalMarksMaximum(), externalMarksDAO.getExternalMarksScored(), optionalSubjectEntity.get().getSem(), optionalDepartmentEntity.get().getId()));
                            }
                        }
                    }
                }
                if (request.size() == listofExternalMarksEntity.size()) {
                    externalMarksRepository.saveAll(listofExternalMarksEntity);
                    return request;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ExternalMarksDAO update(ExternalMarksDAO request, String usn, String subjectName, String departmentName, int sem) {
        try {
            if (usn != null && subjectName != null && departmentName != null && sem != 0) {
                Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findBydepartmentName(departmentName);
                Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(subjectName);
                Optional<StudentEntity> optionalStudentEntity = studentRepository.findByusn(usn);
                if (optionalSubjectEntity.isPresent() && optionalDepartmentEntity.isPresent() && optionalStudentEntity.isPresent()) {
                    Optional<ExternalMarksEntity> optionalExternalMarksEntity =
                            externalMarksRepository.findByusnAndSubjectFKAndSemAndDeptFK(usn, optionalSubjectEntity.get().getSubjectPK(), sem, optionalDepartmentEntity.get().getId());
                    if (optionalExternalMarksEntity.isPresent()) {
                        ExternalMarksEntity externalMarksEntity = optionalExternalMarksEntity.get();
                        if (request.getExternalMarksScored() != 0d) {
                            externalMarksEntity.setExternalMarksScored(request.getExternalMarksScored());
                            this.externalMarksRepository.saveAndFlush(externalMarksEntity);
                            return ExternalMarksDAO.build(usn, subjectName, externalMarksEntity.getExternalMarksMaximum(), externalMarksEntity.getExternalMarksScored(), externalMarksEntity.getSem(), departmentName
                                    , internalMarksRepository.findById(externalMarksEntity.getInternalMarksFK()).get().getMarksScored(), internalMarksRepository.findById(externalMarksEntity.getInternalMarksFK()).get().getMaximumMarks());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ExternalMarksDAO> getByUsnAndSem(String usn, int sem) {
        if (usn != null && sem != 0) {
            Optional<List<ExternalMarksEntity>> optionalExternalMarksEntities = externalMarksRepository.findByUsnAndSem(usn, sem);
            if (optionalExternalMarksEntities.isPresent()) {
                List<ExternalMarksDAO> response = new ArrayList<ExternalMarksDAO>();
                List<ExternalMarksEntity> externalMarksEntities = optionalExternalMarksEntities.get();
                for (ExternalMarksEntity externalMarksEntity : externalMarksEntities) {
                    response.add(ExternalMarksDAO.build(usn, subjectRepository.findById(externalMarksEntity.getSubjectFK()).get().getSubjectName(), externalMarksEntity.getExternalMarksMaximum(), externalMarksEntity.getExternalMarksScored(), externalMarksEntity.getSem(), departmentRepository.findById(externalMarksEntity.getDeptFK()).get().getDepartmentName()
                            , internalMarksRepository.findById(externalMarksEntity.getInternalMarksFK()).get().getMarksScored(), internalMarksRepository.findById(externalMarksEntity.getInternalMarksFK()).get().getMaximumMarks()));

                }
                return response;
            }
        }
        return null;
    }

    @Override
    public List<ExternalMarksDAO> getBySem(int Sem) {
        if (Sem != 0) {
            Optional<List<ExternalMarksEntity>> optionalExternalMarksEntities = externalMarksRepository.findBysem(Sem);
            if (optionalExternalMarksEntities.isPresent()) {
                List<ExternalMarksDAO> response = new ArrayList<ExternalMarksDAO>();
                List<ExternalMarksEntity> externalMarksEntities = optionalExternalMarksEntities.get();
                for (ExternalMarksEntity externalMarksEntity : externalMarksEntities) {
                    response.add(ExternalMarksDAO.build(externalMarksEntity.getUsn(), subjectRepository.findById(externalMarksEntity.getSubjectFK()).get().getSubjectName(), externalMarksEntity.getExternalMarksMaximum(), externalMarksEntity.getExternalMarksScored(), externalMarksEntity.getSem(), departmentRepository.findById(externalMarksEntity.getDeptFK()).get().getDepartmentName()
                            , internalMarksRepository.findById(externalMarksEntity.getInternalMarksFK()).get().getMarksScored(), internalMarksRepository.findById(externalMarksEntity.getInternalMarksFK()).get().getMaximumMarks()));

                }
                return response;
            }
        }
        return null;
    }

    @Override
    public List<ExternalMarksDAO> getByDept(String departmentName) {
        if (departmentName != null) {
            Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findBydepartmentName(departmentName);
            if (optionalDepartmentEntity.isPresent()) {
                Optional<List<ExternalMarksEntity>> optionalExternalMarksEntities = externalMarksRepository.findByDeptFK(optionalDepartmentEntity.get().getId());
                if (optionalExternalMarksEntities.isPresent()) {
                    List<ExternalMarksDAO> response = new ArrayList<ExternalMarksDAO>();
                    List<ExternalMarksEntity> externalMarksEntities = optionalExternalMarksEntities.get();
                    for (ExternalMarksEntity externalMarksEntity : externalMarksEntities) {
                        response.add(ExternalMarksDAO.build(externalMarksEntity.getUsn(), subjectRepository.findById(externalMarksEntity.getSubjectFK()).get().getSubjectName(), externalMarksEntity.getExternalMarksMaximum(), externalMarksEntity.getExternalMarksScored(), externalMarksEntity.getSem(), departmentName
                                , internalMarksRepository.findById(externalMarksEntity.getInternalMarksFK()).get().getMarksScored(), internalMarksRepository.findById(externalMarksEntity.getInternalMarksFK()).get().getMaximumMarks()));

                    }
                    return response;
                }
            }
        }
        return null;
    }
}
