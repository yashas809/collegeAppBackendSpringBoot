package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.SubjectDAO;
import com.collegeManagement.app.entity.DepartmentEntity;
import com.collegeManagement.app.entity.StudentEntity;
import com.collegeManagement.app.entity.SubjectEntity;
import com.collegeManagement.app.repository.DepartmentRepository;
import com.collegeManagement.app.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ISubjectImpl implements ISubject {

    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public SubjectDAO createSubject(SubjectDAO requestData) {
        if (requestData != null) {
            if (requestData.getDeptName() != null) {
                Optional<DepartmentEntity> optionalDeptEntity = departmentRepository.findBydepartmentName(requestData.getDeptName());
                if (optionalDeptEntity.isPresent()) {
                    try {
                        DepartmentEntity deptEntityData = optionalDeptEntity.get();
                        SubjectEntity subjectEntity = SubjectEntity.build(0l, requestData.getSubjectName(),
                                requestData.getSubjectCode(), deptEntityData.getId(), requestData.getSem());
                        subjectRepository.save(subjectEntity);
                        return requestData;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public SubjectDAO updateSubject(String subjectCode, SubjectDAO requestData) {
        try {
            if (subjectCode != null) {
                Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectCode(subjectCode);
                if (optionalSubjectEntity.isPresent()) {
                    SubjectEntity subjectEntityentityData = optionalSubjectEntity.get();
                    if (requestData.getSubjectCode() != null) {
                        subjectEntityentityData.setSubjectCode(requestData.getSubjectCode());
                    }
                    if (requestData.getSubjectName() != null) {
                        subjectEntityentityData.setSubjectName(requestData.getSubjectName());
                    }
                    if (requestData.getDeptName() != null) {
                        Optional<DepartmentEntity> optionalDeptEntity = departmentRepository.findBydepartmentName(requestData.getDeptName());
                        if (optionalDeptEntity.isPresent()) {
                            subjectEntityentityData.setDeptFK(optionalDeptEntity.get().getId());
                        }
                    }
                    if (requestData.getSem() != 0) {
                        subjectEntityentityData.setSem(requestData.getSem());
                    }
                    subjectRepository.saveAndFlush(subjectEntityentityData);
                    optionalSubjectEntity = subjectRepository.findById(subjectEntityentityData.getSubjectPK());
                    if (optionalSubjectEntity.isPresent()) {
                        subjectEntityentityData = optionalSubjectEntity.get();
                        return SubjectDAO.build(subjectEntityentityData.getSubjectName(), subjectEntityentityData.getSubjectCode(),
                                departmentRepository.findById(subjectEntityentityData.getDeptFK()).get().getDepartmentName(), subjectEntityentityData.getSem());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SubjectDAO> getSubjectsBySem(int Sem,String deptName) {
        long deptId = 0L;
        if(deptName!=null)
        {
            deptId=this.departmentRepository.findBydepartmentName(deptName).get().getId();
        }
        Optional<List<SubjectEntity>> optionalSubjectEntity = subjectRepository.findBysemAndDeptFK(Sem,deptId);
        if (optionalSubjectEntity.isPresent()) {
            List<SubjectDAO> responseData = new ArrayList<SubjectDAO>();
            List<SubjectEntity> listofSubjectEntity = optionalSubjectEntity.get();
            for (SubjectEntity entityData : listofSubjectEntity) {
                responseData.add(SubjectDAO.build(entityData.getSubjectName(), entityData.getSubjectCode(),
                        departmentRepository.findById(entityData.getDeptFK()).get().getDepartmentName(), entityData.getSem()));
            }
            return responseData;
        }
        return null;
    }

    @Override
    public SubjectDAO getSubjectByName(String subjectName) {
        Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(subjectName);
        if (optionalSubjectEntity.isPresent()) {
            SubjectEntity listofSubjectEntity = optionalSubjectEntity.get();
            return (SubjectDAO.build(listofSubjectEntity.getSubjectName(), listofSubjectEntity.getSubjectCode(),
                    departmentRepository.findById(listofSubjectEntity.getDeptFK()).get().getDepartmentName(), listofSubjectEntity.getSem()));
        }
        return null;
    }

    @Override
    public SubjectDAO getSubjectBySubjectCode(String subjectCode) {
        Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectCode(subjectCode);
        if (optionalSubjectEntity.isPresent()) {
            SubjectEntity listofSubjectEntity = optionalSubjectEntity.get();
            return (SubjectDAO.build(listofSubjectEntity.getSubjectName(), listofSubjectEntity.getSubjectCode(),
                    departmentRepository.findById(listofSubjectEntity.getDeptFK()).get().getDepartmentName(), listofSubjectEntity.getSem()));
        }
        return null;
    }

    @Override
    public boolean deleteSubject(String subjectCode) {
        try {
            if (subjectCode != null) {
                Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectCode(subjectCode);
                if (optionalSubjectEntity.isPresent()) {
                    SubjectEntity subjectEntityentityData = optionalSubjectEntity.get();
                    subjectRepository.delete(subjectEntityentityData);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
