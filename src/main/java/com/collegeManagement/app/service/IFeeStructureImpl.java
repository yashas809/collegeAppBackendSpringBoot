package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.FeeStructure;
import com.collegeManagement.app.entity.DepartmentEntity;
import com.collegeManagement.app.entity.FeeStructureEntity;
import com.collegeManagement.app.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.collegeManagement.app.repository.FeeStructureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IFeeStructureImpl implements IFeeStructure{

    @Autowired
    FeeStructureRepository feeRepo;

    @Autowired
    DepartmentRepository deptRepo;


    @Override
    public FeeStructure create(FeeStructure request)
    {
        try
        {
            if(request!=null)
            {
                if(request.getDepartmentName()!=null)
                {
                    Optional<DepartmentEntity> deptEntity = deptRepo.findBydepartmentName(request.getDepartmentName());
                    Optional<FeeStructureEntity> optionalEntt = feeRepo.findBydeptFKAndSem(deptEntity.get().getId(), request.getSem());
                    if(deptEntity.isPresent() && optionalEntt.isEmpty())
                    {
                        FeeStructureEntity feeStructureEntityData = FeeStructureEntity.build(0l,deptEntity.get().getId(),
                                request.getSem(), request.getSemfee());
                        feeStructureEntityData = feeRepo.save(feeStructureEntityData);
                        return FeeStructure.build(deptEntity.get().getDepartmentName(),request.getSem(), request.getSemfee());
                    }
                }
            }
        }catch (Exception exp)
        {
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FeeStructure> getFeeStructureDepartmentWise(String departmentName)
    {
        if(departmentName!=null)
        {
            Optional<DepartmentEntity> deptEntity = deptRepo.findBydepartmentName(departmentName);
            if(deptEntity.isPresent())
            {
                List<FeeStructureEntity> feeStructureEntitydata = feeRepo.findBydeptFK(deptEntity.get().getId());

                List<FeeStructure> response = new ArrayList<FeeStructure>();

                for(int i = 0 ; i< feeStructureEntitydata.size() ; i++)
                {
                    FeeStructure feeStructure = FeeStructure.build(departmentName,feeStructureEntitydata.get(i).getSem(),feeStructureEntitydata.get(i).getSemFee());
                    response.add(feeStructure);

                }
                return response;
            }
        }
        return null;
    }

    @Override
    public boolean update(String departmentName, int sem, double fee)
    {
        if(deptRepo.findBydepartmentName(departmentName).isPresent() && sem!=0)
        {
           Optional<FeeStructureEntity> optionalentityData = feeRepo.findBydeptFKAndSem(deptRepo.findBydepartmentName(departmentName).get().getId(),sem);
           if(optionalentityData.isPresent())
           {
               FeeStructureEntity entityData = optionalentityData.get();
               entityData.setSemFee(fee);
               feeRepo.saveAndFlush(entityData);
           }


           return true;
        }

        return false;
    }

    @Override
    public boolean delete(String departmentName, int sem) {

        if(deptRepo.findBydepartmentName(departmentName).isPresent() && sem!=0)
        {
            Optional<FeeStructureEntity> OptionalentityData = feeRepo.findBydeptFKAndSem(deptRepo.findBydepartmentName(departmentName).get().getId(),sem);
            if(OptionalentityData.isPresent())
            {
                FeeStructureEntity entityData = OptionalentityData.get();
                feeRepo.deleteById(entityData.getFeeStructurePK());
                return true;
            }


        }
        return false;
    }
}
