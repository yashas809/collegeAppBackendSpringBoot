package com.collegeManagement.app.service;

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
import java.util.List;
import java.util.Optional;

@Service
public class IStudentFeeServiceImpl implements IStudentFeeService
{

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
    public StudentFee createEntry(StudentFee request)
    {
        try {

            if(request.getUsn()!=null && request.getSem()!=0 && request.getDeptName()!=null)
            {
                Optional<StudentEntity> optionalStudentEntity = studentRepository.findByusn(request.getUsn());
                Optional<FeeStructureEntity> optionalFeeStructureEntity= feeStructureRepository.findBydeptFKAndSem(departmentRepository.findBydepartmentName(request.getDeptName()).get().getId(), request.getSem());
                if(optionalFeeStructureEntity.isPresent() && optionalStudentEntity.isPresent())
                {
                    StudentEntity studentEntityData = optionalStudentEntity.get();
                    FeeStructureEntity feeStructureEntityData = optionalFeeStructureEntity.get();
                    FeeReceipt FeeReceiptentityData =FeeReceipt.build(0l, request.getFileData(), request.getFileName());
                    FeeReceiptentityData = studentFeeReceipt.save(FeeReceiptentityData);
                    if(FeeReceiptentityData.getFeeReceiptPK()!=0l)
                    {
                        StudentFeeEntity studentFeeEntityData = StudentFeeEntity.build(0l,feeStructureEntityData.getFeeStructurePK(),studentEntityData.getStudentPK(),request.isFeePending(), request.getFeePaid(),
                                FeeReceiptentityData.getFeeReceiptPK(), LocalDate.now());

                        studentFeeEntityData = studentFeeRepository.save(studentFeeEntityData);

                        return request;
                    }

                }
            }
            return null;
        }catch (Exception exp)
        {
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentFee> getStudentFeeDetails(String usn) {
        return null;
    }

    @Override
    public List<StudentFee> getAllFeeData() {
        return null;
    }

    @Override
    public byte[] downloadFile(long id) {

        return studentFeeReceipt.findById(id).get().getReceiptFile();

    }
}
