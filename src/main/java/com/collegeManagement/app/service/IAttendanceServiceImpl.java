package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.AttendanceDAO;
import com.collegeManagement.app.entity.AttendanceEntity;
import com.collegeManagement.app.entity.StudentEntity;
import com.collegeManagement.app.entity.SubjectEntity;
import com.collegeManagement.app.repository.AttendanceRepository;
import com.collegeManagement.app.repository.DepartmentRepository;
import com.collegeManagement.app.repository.StudentRepository;
import com.collegeManagement.app.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IAttendanceServiceImpl implements IAttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    DepartmentRepository deptRepo;


    @Override
    public AttendanceDAO create(AttendanceDAO request) {
        try {
            if (request != null && (request.getUsn() != null && request.getSubjectName() != null)) {
                    Optional<StudentEntity> optionalStudentEntity = studentRepository.findByusn(request.getUsn());
                    Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(request.getSubjectName());
                    Optional<AttendanceEntity> optionalAttendanceEntity = attendanceRepository.findByUsnAndSemAndSubjectFK(request.getUsn(), request.getSem(), optionalSubjectEntity.get().getSubjectPK());
                    if (optionalStudentEntity.isPresent() && optionalSubjectEntity.isPresent() && optionalAttendanceEntity.isEmpty()) {
                        request.setAttendancePercentage(this.averageAttendanceCalculator(request.getTotalNumberOfClasses(), request.getNoOfClassesAttended()));
                        AttendanceEntity entityData = AttendanceEntity.build(0L, request.getUsn(), optionalSubjectEntity.get().getSubjectPK(), request.getSem(),
                                request.getTotalNumberOfClasses(), request.getNoOfClassesAttended(), request.getAttendancePercentage());
                        entityData = attendanceRepository.save(entityData);
                        request.setId(entityData.getAttendancePK());
                        return request;
                    }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AttendanceDAO> getAttendanceByUSNAndSem(String usn, int sem) {
        try {
            Optional<List<AttendanceEntity>> optionalListAttendanceEntity = attendanceRepository.findByUsnAndSem(usn, sem);
            if (optionalListAttendanceEntity.isPresent()) {
                List<AttendanceEntity> listOfAttendance = optionalListAttendanceEntity.get();
                List<AttendanceDAO> response = new ArrayList<AttendanceDAO>();
                for (AttendanceEntity attendanceEntity : listOfAttendance) {
                    response.add(AttendanceDAO.build(attendanceEntity.getAttendancePK(), attendanceEntity.getUsn(), subjectRepository.findById(attendanceEntity.getSubjectFK()).get().getSubjectName(),
                            attendanceEntity.getSem(), attendanceEntity.getAttendancePercentage(), attendanceEntity.getAttendedClasses(),attendanceEntity.getTotalNumberOfClasses()));
                }
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<List<AttendanceDAO>> getAttendanceByDeptAndSem(String departmentName, int sem) {
       Optional<List<StudentEntity>> optionalListStudentEntity = studentRepository.findBySemAndDeptfk(sem,deptRepo.findBydepartmentName(departmentName).get().getId());
       if(optionalListStudentEntity.isPresent())
       {
           List<List<AttendanceDAO>> response = new ArrayList<>();
           List<StudentEntity> listofStudentEntity = optionalListStudentEntity.get();
           boolean flag = false;
           for(StudentEntity studentEntity: listofStudentEntity)
           {
               List<AttendanceDAO> listofAttendance = getAttendanceByUSNAndSem(studentEntity.getUsn(), studentEntity.getSem());
               if(!listofAttendance.isEmpty())
               {
                   flag = true;
                   response.add(listofAttendance);
               }
           }
           if(flag){
               return response;
           }
           return null;
       }
       return null;
    }

    @Override
    public AttendanceDAO getAttendanceSubjectWise(String usn, String subjectName, int sem) {
        Optional<AttendanceEntity> attendanceEntity= attendanceRepository.findByUsnAndSemAndSubjectFK(usn,sem,subjectRepository.findBysubjectName(subjectName).get().getSubjectPK());
        if(attendanceEntity.isPresent()) {
            AttendanceEntity entityData = attendanceEntity.get();
            return AttendanceDAO.build(entityData.getAttendancePK(),usn,subjectName,sem,entityData.getAttendancePercentage(),
                    entityData.getAttendedClasses(), entityData.getTotalNumberOfClasses());
        }
        return null;
    }

    @Override
    public AttendanceDAO update(String usn, String subjectName, int sem, AttendanceDAO request) {
        if(request!=null)
        {
           Optional<AttendanceEntity> attendanceEntity= attendanceRepository.findByUsnAndSemAndSubjectFK(usn,sem,subjectRepository.findBysubjectName(subjectName).get().getSubjectPK());
           if(attendanceEntity.isPresent())
           {
                AttendanceEntity entityData = attendanceEntity.get();
                if(request.getNoOfClassesAttended()!=0 && request.getTotalNumberOfClasses()!=0)
                {
                    entityData.setAttendedClasses(request.getNoOfClassesAttended());
                    entityData.setTotalNumberOfClasses(request.getTotalNumberOfClasses());
                    entityData.setAttendancePercentage(this.averageAttendanceCalculator(request.getTotalNumberOfClasses(), request.getNoOfClassesAttended()));
                }
                entityData = attendanceRepository.saveAndFlush(entityData);
                return AttendanceDAO.build(entityData.getAttendancePK(),usn,subjectName,sem,entityData.getAttendancePercentage(), entityData.getAttendedClasses(),entityData.getTotalNumberOfClasses());
           }
        }
        return null;
    }

    @Override
    public boolean delete(String usn, int sem, String subjectName) {
        return false;
    }

    public double averageAttendanceCalculator(long totalNoOfClasses, long attendedClasses) {
        return ((double) attendedClasses / totalNoOfClasses) * 100;
    }
}
