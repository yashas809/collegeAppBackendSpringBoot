package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.FeeReceiptDAO;
import com.collegeManagement.app.dao.FeeStructure;
import com.collegeManagement.app.dao.StudentFee;
import com.collegeManagement.app.entity.FeeReceipt;
import com.collegeManagement.app.entity.FeeStructureEntity;
import com.collegeManagement.app.entity.StudentEntity;
import com.collegeManagement.app.entity.StudentFeeEntity;
import com.collegeManagement.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IStudentFeeServiceImpl implements IStudentFeeService {

    @Autowired
    StudentFeeRepository studentFeeRepository;

    @Autowired
    StudentFeeReceipt studentFeeReceipt;

    @Autowired
    FeeStructureRepository feeStructureRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public StudentFee createEntry(StudentFee request) {
        try {

            if (request.getUsn() != null && request.getSem() != 0 && request.getDeptName() != null) {
                Optional<StudentEntity> optionalStudentEntity = studentRepository.findByusn(request.getUsn());
                Optional<FeeStructureEntity> optionalFeeStructureEntity = feeStructureRepository.findBydeptFKAndSem(departmentRepository.findBydepartmentName(request.getDeptName()).get().getId(), request.getSem());
                if (optionalFeeStructureEntity.isPresent() && optionalStudentEntity.isPresent()) {
                    StudentEntity studentEntityData = optionalStudentEntity.get();
                    FeeStructureEntity feeStructureEntityData = optionalFeeStructureEntity.get();
                    FeeReceipt FeeReceiptentityData = FeeReceipt.build(0l, request.getFileData(), request.getFileName());
                    FeeReceiptentityData = studentFeeReceipt.save(FeeReceiptentityData);
                    if (FeeReceiptentityData.getFeeReceiptPK() != 0l) {
                        StudentFeeEntity studentFeeEntityData = StudentFeeEntity.build(0l, feeStructureEntityData.getFeeStructurePK(), studentEntityData.getStudentPK(), request.isFeePending(), request.getFeePaid(),
                                FeeReceiptentityData.getFeeReceiptPK(), LocalDate.now());
                        studentFeeEntityData = studentFeeRepository.save(studentFeeEntityData);
                        request.setReceiptFK(studentFeeEntityData.getReceiptFK());
                        return request;
                    }
                }
            }
            return null;
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentFee> getStudentFeeDetails(String usn) {
        if (usn != null) {
            Optional<StudentEntity> optionalStudentEntity = studentRepository.findByusn(usn);
            if (optionalStudentEntity.isPresent()) {
                StudentEntity studentEntity = optionalStudentEntity.get();
                Optional<List<StudentFeeEntity>> optionalStudentFeeRepository = studentFeeRepository.findBystudentFK(studentEntity.getStudentPK());
                List<StudentFee> response = new ArrayList<StudentFee>();
                if (optionalStudentFeeRepository.isPresent()) {
                    List<StudentFeeEntity> studentFeeEntities = optionalStudentFeeRepository.get();
                    for (StudentFeeEntity i : studentFeeEntities) {
                        if (i.getReceiptFK() != 0l) {
                            Optional<FeeReceipt> feeReceiptentityoptional = studentFeeReceipt.findById(i.getReceiptFK());
                            if (feeReceiptentityoptional.isPresent()) {
                                StudentFee entityData = StudentFee.build(usn, studentEntity.getSem(), departmentRepository.findById(studentEntity.getDeptfk()).get().getDepartmentName(),
                                        null, feeReceiptentityoptional.get().getFileName(),
                                        i.isFeePending(), i.getReceiptFK(), studentEntity.getStudentName(), i.getTotalFeePaid(), i.getCreatedDate());
                                response.add(entityData);
                            }
                        }
                    }
                    return response;
                }
            }
        }
        return null;
    }


    public List<StudentFee> getStudentFeeDetailsbySem(int sem) {
        if (sem != 0) {
            Optional<List<StudentEntity>> optionalStudentEntity = studentRepository.findBySem(sem);
            if (optionalStudentEntity.isPresent()) {
                List<StudentEntity> studentEntity = optionalStudentEntity.get();

                for(StudentEntity stEntity : studentEntity){
                Optional<List<StudentFeeEntity>> optionalStudentFeeRepository = studentFeeRepository.findBystudentFK(stEntity.getStudentPK());
                List<StudentFee> response = new ArrayList<StudentFee>();
                if (optionalStudentFeeRepository.isPresent()) {
                    List<StudentFeeEntity> studentFeeEntities = optionalStudentFeeRepository.get();
                    for (StudentFeeEntity i : studentFeeEntities) {
                        if (i.getReceiptFK() != 0l) {
                            Optional<FeeReceipt> feeReceiptentityoptional = studentFeeReceipt.findById(i.getReceiptFK());
                            if (feeReceiptentityoptional.isPresent()) {
                                StudentFee entityData = StudentFee.build(stEntity.getUsn(), stEntity.getSem(), departmentRepository.findById(stEntity.getDeptfk()).get().getDepartmentName(),
                                        null, feeReceiptentityoptional.get().getFileName(),
                                        i.isFeePending(), i.getReceiptFK(), stEntity.getStudentName(), i.getTotalFeePaid(), i.getCreatedDate());
                                response.add(entityData);
                            }
                        }
                    }
                    return response;
                }
            }
            }
        }
        return null;
    }

    @Override
    public List<StudentFee> getAllFeeData(int sem, String deptName) {

                System.out.println(departmentRepository.findBydepartmentName(deptName).get().getId());
                Optional<List<StudentEntity>> OptionalstudentEntity = studentRepository.findBySemAndDeptfk(sem, departmentRepository.findBydepartmentName(deptName).get().getId());
                if(OptionalstudentEntity.isPresent())
                {
                    List<StudentFee> response = new ArrayList<StudentFee>();
                    List<StudentEntity> studentEntity = OptionalstudentEntity.get();
                    for(StudentEntity stEntity : studentEntity) {
                        Optional<List<StudentFeeEntity>> optionalStudentFeeRepository = studentFeeRepository.findBystudentFK(stEntity.getStudentPK());

                        if (optionalStudentFeeRepository.isPresent()) {
                            List<StudentFeeEntity> studentFeeEntities = optionalStudentFeeRepository.get();
                            for (StudentFeeEntity i : studentFeeEntities) {
                                if (i.getReceiptFK() != 0l) {
                                    Optional<FeeReceipt> feeReceiptentityoptional = studentFeeReceipt.findById(i.getReceiptFK());
                                    if (feeReceiptentityoptional.isPresent()) {
                                        StudentFee entityData = StudentFee.build(stEntity.getUsn(), stEntity.getSem(), departmentRepository.findById(stEntity.getDeptfk()).get().getDepartmentName(),
                                                null, feeReceiptentityoptional.get().getFileName(),
                                                i.isFeePending(), i.getReceiptFK(), stEntity.getStudentName(), i.getTotalFeePaid(), i.getCreatedDate());
                                        response.add(entityData);
                                    }
                                }
                            }

                        }
                    }
                    return response;
                }

        return null;
    }

    @Override
    public FeeReceiptDAO downloadFile(long id) {

        try{

            Optional<FeeReceipt> optionalFeeReceipt = studentFeeReceipt.findById(id);
            if(optionalFeeReceipt.isPresent())
            {
                FeeReceipt feeReceipt = optionalFeeReceipt.get();
                return FeeReceiptDAO.build(feeReceipt.getReceiptFile(), feeReceipt.getFileName());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
