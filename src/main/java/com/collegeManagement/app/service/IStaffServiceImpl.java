package com.collegeManagement.app.service;


import com.collegeManagement.app.dao.Login;
import com.collegeManagement.app.dao.Staff;
import com.collegeManagement.app.entity.DepartmentEntity;
import com.collegeManagement.app.entity.LoginEntity;
import com.collegeManagement.app.entity.RoleEntity;
import com.collegeManagement.app.entity.StaffEntity;
import com.collegeManagement.app.repository.DepartmentRepository;
import com.collegeManagement.app.repository.RoleRepository;
import com.collegeManagement.app.repository.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class IStaffServiceImpl implements IStaffService
{

    @Autowired
    StaffRepository staffreposiotry;

    @Autowired
    DepartmentRepository deptRepository;

    @Autowired
    RoleRepository rolerepository;

    @Autowired
    ILogin loginService;

    @Autowired
    Staff staff;

    @Autowired
    LoginEntity loginentity;

    @Autowired
    RoleEntity roleentitydata;

    @Autowired
    Optional<DepartmentEntity> deptEntity;

    @Autowired
    StaffEntity entityData;


    @Autowired
    Login login;


    @Transactional
    @Override
    public StaffEntity createStaff(Staff request)
    {
        try{

            if(request.getRoleName()!=null)
            {
                roleentitydata = rolerepository.findByroleName(request.getRoleName());
            }

            if(request.getDepartmentName()!=null)
            {
               deptEntity =  deptRepository.findBydepartmentName(request.getDepartmentName());
            }

            if(request.getLoginName()!=null && request.getPassword()!=null)
            {
                login.setLoginName(request.getLoginName());
                login.setPassword(request.getPassword());
                loginentity = loginService.createLogin(login);
            }

            if(loginentity!=null && roleentitydata!=null && deptEntity.isPresent())
            {
                entityData = StaffEntity.build(0l, request.getName(), LocalDateTime.now(),false,deptEntity.get().getId(),loginentity.getLoginPK(), roleentitydata.getRolePK());
                entityData = staffreposiotry.save(entityData);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return entityData;
    }

    @Override
    public List<Staff> getAll() {
        return null;
    }

    @Override
    public Staff getStaffByName(String staffName) {

        if(staffName!=null)
        {
            entityData  = staffreposiotry.findByName(staffName);
            if(entityData!=null)
            {

                return Staff.build(entityData.getName(),entityData.getCreatedDate(),entityData.getDelflag(),
                        deptRepository.findById(entityData.getDeptfk()).get().getDepartmentName(),
                        loginService.findByloginPK(entityData.getRolefk()).getLoginName(),"",
                        rolerepository.findById(entityData.getRolefk()).get().getRoleName());
            }
        }

        return null;

    }

    @Override
    public Staff updateStaff(long StaffId, Staff request)
    {
        try {
            if(StaffId!=0l)
            {
                Optional<StaffEntity> optionalStaffEntity  = staffreposiotry.findById(StaffId);
                optionalStaffEntity.ifPresent(staffEntity -> entityData = staffEntity);
                if(request.getName()!=null)
                {
                    entityData.setName(request.getName());
                }

                if(request.getDelflag()!=null)
                {
                    entityData.setDelflag(request.getDelflag());
                }

                if(request.getDepartmentName()!=null)
                {
                    Optional<DepartmentEntity> deptEntity = deptRepository.findBydepartmentName(request.getDepartmentName());
                    if(deptEntity.isPresent())
                    {
                        entityData.setDeptfk(deptEntity.get().getId());
                    }else {
                        return null;
                    }
                }

                entityData = staffreposiotry.saveAndFlush(entityData);
                return this.getStaffByName(entityData.getName());
            }

            return  null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Staff StaffLogin(String userName, String password)
    {
        try {
            boolean flag = false;
            if(userName!=null && password!=null)
            {
                loginentity = loginService.findByloginName(userName);
                if(loginentity!= null)
                {
                    flag = loginService.verifyPassword(password, loginentity.getPassword(), loginentity.getSecretKey());
                }
            }
            if(flag)
            {
                if(loginentity.getLoginPK()!=0l)
                {
                    entityData = staffreposiotry.findByloginfk(loginentity.getLoginPK());
                    if(entityData!=null && !entityData.getDelflag())
                    {
                        deptEntity = deptRepository.findById(entityData.getDeptfk());

                        Optional<RoleEntity> roleData = rolerepository.findById(entityData.getRolefk());
                        roleData.ifPresent(roleEntity -> roleentitydata = roleEntity);

                        if(deptEntity.isPresent())
                        {
                            staff = Staff.build(entityData.getName(), entityData.getCreatedDate(), entityData.getDelflag(), deptEntity.get().getDepartmentName()
                                    ,userName,null,roleentitydata.getRoleName());
                            return staff;
                        }


                    }
                }
            }
            return null;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
