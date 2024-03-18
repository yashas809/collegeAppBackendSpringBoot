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


    @Override
    public AttendanceDAO create(AttendanceDAO request) {
        try {
            if (request != null) {
                if (request.getUsn() != null && request.getSubjectName() != null) {
                    Optional<StudentEntity> optionalStudentEntity = studentRepository.findByusn(request.getUsn());
                    Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(request.getSubjectName());
                    if (optionalStudentEntity.isPresent() && optionalSubjectEntity.isPresent()) {
                        request.setAttendancePercentage(this.averageAttendanceCalculator(request.getTotalNumberOfClasses(), request.getNoOfClassesAttended()));
                        AttendanceEntity entityData = AttendanceEntity.build(0l, request.getUsn(), optionalSubjectEntity.get().getSubjectPK(), request.getSem(),
                                request.getTotalNumberOfClasses(), request.getNoOfClassesAttended(), request.getAttendancePercentage());
                        entityData = attendanceRepository.save(entityData);
                        request.setId(entityData.getAttendancePK());
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
    public List<AttendanceDAO> getAttendanceByUSNAndSem(String usn, int sem) {
        try {
            Optional<List<AttendanceEntity>> optionalListAttendanceEntity = attendanceRepository.findByUsnAndSem(usn, sem);
            if (optionalListAttendanceEntity.isPresent()) {
                List<AttendanceEntity> listOfAttendance = optionalListAttendanceEntity.get();
                List<AttendanceDAO> response = new ArrayList<AttendanceDAO>();
                for (AttendanceEntity attendanceEntity : listOfAttendance) {
                    response.add(AttendanceDAO.build(attendanceEntity.getAttendancePK(), attendanceEntity.getUsn(), subjectRepository.findById(attendanceEntity.getSubjectFK()).get().getSubjectName(),
                            attendanceEntity.getSem(), attendanceEntity.getTotalNumberOfClasses(), attendanceEntity.getAttendedClasses(), attendanceEntity.getAttendancePercentage()));
                }
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AttendanceDAO update(String usn, String subjectName, int sem, AttendanceDAO request) {
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
