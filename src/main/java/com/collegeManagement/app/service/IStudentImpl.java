package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.Student;
import com.collegeManagement.app.dao.Login;
import com.collegeManagement.app.entity.DepartmentEntity;
import com.collegeManagement.app.entity.LoginEntity;
import com.collegeManagement.app.entity.StudentEntity;
import com.collegeManagement.app.enums.studentEnum;
import com.collegeManagement.app.repository.DepartmentRepository;
import com.collegeManagement.app.repository.RoleRepository;
import com.collegeManagement.app.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IStudentImpl implements IStudent
{

    @Autowired
    ILogin loginService;

    @Autowired
    Login loginRequestData;

    @Autowired
    LoginEntity loginEntityData;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    DepartmentRepository departmentRepo;

    @Autowired
    StudentRepository studentRepository;

    @Override
    @Transactional
    public Student createStudent(Student requestData) {

        try{

            if(requestData.getDepartmentName()!=null)
            {
                Optional<DepartmentEntity> deptEntityData = departmentRepo.findBydepartmentName(requestData.getDepartmentName());
                Optional<StudentEntity> checkForUSN = studentRepository.findByusn(requestData.getUsn());
                if(checkForUSN.isPresent())
                {
                    return null;
                }
                if(deptEntityData.isPresent())
                {
                    if(requestData.getUsn()!=null && requestData.getPassword()!=null)
                    {
                        loginRequestData.setLoginName(requestData.getUsn());
                        loginRequestData.setPassword(requestData.getPassword());
                        loginEntityData = loginService.createLogin(loginRequestData);
                    }

                    if(loginEntityData!=null)
                    {
                        int status = 0;
                        int gender = 0;
                        if(requestData.getStatus()!=null && requestData.getGender()!=null)
                        {
                            status = studentEnum.Status.valueOf(requestData.getStatus()).ordinal();
                            gender = studentEnum.gender.valueOf(requestData.getGender()).ordinal();
                        }

                        StudentEntity studententityData = StudentEntity.build(0l, requestData.getUsn(), requestData.getSem(), deptEntityData.get().getId(), LocalDate.now(),
                                loginEntityData.getLoginPK(),roleRepo.findByroleName("Student").getRolePK(),status,
                                requestData.getStudentName(),requestData.getDateOfBirth(),gender,requestData.getEmailID());

                        studententityData = studentRepository.save(studententityData);
                        requestData.setStudentID(studententityData.getStudentPK());
                        requestData.setPassword("");
                       return requestData;
                    }
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
       return null;
    }

    @Override
    public Student GetStudent(String usn) {

        Optional<StudentEntity> entityData = studentRepository.findByusn(usn);
        if(entityData.isPresent())
        {
            studentEnum.Status status = null;
            studentEnum.gender gender = null;
            StudentEntity studentEntity = entityData.get();
            if(studentEntity.getStatus()==1l)
            {
                status = studentEnum.Status.ACTIVE;
            }else
            {
                status = studentEnum.Status.INACTIVE;
            }

            if(studentEntity.getGender()==1l)
            {
                gender = studentEnum.gender.MALE;
            }else{
                gender = studentEnum.gender.FEMALE;
            }

            Student response = Student.build(studentEntity.getUsn(),studentEntity.getSem(),departmentRepo.findById(studentEntity.getDeptfk()).get().getDepartmentName()
            ,loginService.findByloginPK(studentEntity.getLoginFK()).getLoginName(),status.name(),studentEntity.getStudentName(),studentEntity.getDateOfBirth(),gender.name(),studentEntity.getStudentEmailID(),"",studentEntity.getStudentPK());

            return response;
        }
        return null;
    }

    @Override
    public Student GetStudentSemSummary(String usn) {
        return null;
    }

    @Override
    public List<Student> getAllStudentsBasedOnSem(int sem) {

        Optional<List<StudentEntity>> entityData = studentRepository.findBySem(sem);
        if(entityData.isPresent())
        {

            List<StudentEntity> listOfStudentEntity = entityData.get();
            List<Student> listofStudentDAO = new ArrayList<Student>();

            for(int i = 0 ; i < listOfStudentEntity.size() ; i++)
            {
                studentEnum.Status status = null;
                studentEnum.gender gender = null;
                if(listOfStudentEntity.get(i).getStatus()==1l)
                {
                    status = studentEnum.Status.ACTIVE;
                }else
                {
                    status = studentEnum.Status.INACTIVE;
                }

                if(listOfStudentEntity.get(i).getGender()==1l)
                {
                    gender = studentEnum.gender.MALE;
                }else{
                    gender = studentEnum.gender.FEMALE;
                }

                Student response = Student.build(listOfStudentEntity.get(i).getUsn(),listOfStudentEntity.get(i).getSem(),
                        departmentRepo.findById(listOfStudentEntity.get(i).getDeptfk()).get().getDepartmentName()
                        ,loginService.findByloginPK(listOfStudentEntity.get(i).getLoginFK()).getLoginName(),status.name(),listOfStudentEntity.get(i).getStudentName(),listOfStudentEntity.get(i).getDateOfBirth(),gender.name(),
                        listOfStudentEntity.get(i).getStudentEmailID(),"",listOfStudentEntity.get(i).getStudentPK());

                listofStudentDAO.add(response);

            }

            return listofStudentDAO;
        }

        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<StudentEntity> entityData = studentRepository.findAll();

            List<Student> listofStudentDAO = new ArrayList<Student>();
            for (StudentEntity entityDatum : entityData) {
            studentEnum.Status status = null;
            studentEnum.gender gender = null;
            if (entityDatum.getStatus() == 1l) {
                status = studentEnum.Status.ACTIVE;
            } else {
                status = studentEnum.Status.INACTIVE;
            }

            if (entityDatum.getGender() == 1l) {
                gender = studentEnum.gender.MALE;
            } else {
                gender = studentEnum.gender.FEMALE;
            }

            Student response = Student.build(entityDatum.getUsn(), entityDatum.getSem(),
                    departmentRepo.findById(entityDatum.getDeptfk()).get().getDepartmentName()
                    , loginService.findByloginPK(entityDatum.getLoginFK()).getLoginName(), status.name(), entityDatum.getStudentName(), entityDatum.getDateOfBirth(), gender.name(),
                    entityDatum.getStudentEmailID(), "", entityDatum.getStudentPK());

            listofStudentDAO.add(response);

        }
            return listofStudentDAO;

    }

    @Override
    public Student updateStudent(String usn, Student requestData) {
        try {
            if (usn != null && requestData.getUsn() == null) {
                Optional<StudentEntity> optionalstudententitydata = studentRepository.findByusn(usn);

                if (optionalstudententitydata.isPresent()) {
                    StudentEntity entityData = optionalstudententitydata.get();

                    /*Checking if Department is getting Updated*/
                    if (requestData.getDepartmentName() != null) {
                        Optional<DepartmentEntity> deptEntity = departmentRepo.findBydepartmentName(requestData.getDepartmentName());
                        if (deptEntity.isPresent()) {
                            entityData.setDeptfk(deptEntity.get().getId());
                        } else {
                            return null;
                        }
                    }

                    /*Checking if Sem is present*/
                    if (requestData.getSem() != 0) {
                        entityData.setSem(requestData.getSem());
                    }

                    /*Checking if Name is present*/
                    if (requestData.getStudentName() != null) {
                        entityData.setStudentName(requestData.getStudentName());
                    }

                    /*Checking if Email Needs to update*/
                    if (requestData.getEmailID() != null) {
                        entityData.setStudentEmailID(requestData.getEmailID());
                    }

                    /*Checking if gender needs to be updated*/
                    if (requestData.getGender() != null) {
                        int gender = 0;
                        gender = studentEnum.gender.valueOf(requestData.getGender()).ordinal();
                        entityData.setGender(gender);
                    }

                    /*Checking if status needs to be updated*/
                    if (requestData.getStatus() != null) {
                        int status = 0;
                        status = studentEnum.Status.valueOf(requestData.getStatus()).ordinal();
                        entityData.setStatus(status);
                    }

                    entityData = studentRepository.saveAndFlush(entityData);
                    return this.GetStudent(usn);
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Student login(String userName, String password) {

        if(userName!=null && password!=null)
        {
            Optional<StudentEntity> optionalstudententitydata = studentRepository.findByusn(userName);
            if(optionalstudententitydata.isPresent())
            {
                StudentEntity studententity = optionalstudententitydata.get();
                LoginEntity loginentityData = loginService.findByloginPK(studententity.getLoginFK());
                boolean loginsuccess = loginService.verifyPassword(password,loginentityData.getPassword(),loginentityData.getSecretKey());
                if(loginsuccess)
                {
                    return this.GetStudent(userName);
                }
            }else{
                return null;
            }
        }
        return null;
    }

}
