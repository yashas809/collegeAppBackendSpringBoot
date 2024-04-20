package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.TimeTableDAO;
import com.collegeManagement.app.entity.DepartmentEntity;
import com.collegeManagement.app.entity.StaffEntity;
import com.collegeManagement.app.entity.SubjectEntity;
import com.collegeManagement.app.entity.TimeTableEntity;
import com.collegeManagement.app.repository.DepartmentRepository;
import com.collegeManagement.app.repository.StaffRepository;
import com.collegeManagement.app.repository.SubjectRepository;
import com.collegeManagement.app.repository.TimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ITimeTableServiceImpl implements ITimeTableService {

    @Autowired
    TimeTableRepository timeTableRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Transactional
    @Override
    public TimeTableDAO create(TimeTableDAO request) {
        try {
            if (request != null && request.getDeptName() != null && request.getStaffName() != null && request.getSubjectName() != null) {
                Optional<DepartmentEntity> optonaldepartmentEntity = departmentRepository.findBydepartmentName(request.getDeptName());
                Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(request.getSubjectName());
                StaffEntity staffEntity = staffRepository.findByName(request.getStaffName());
                Optional<TimeTableEntity> optionalTimeTableEntity = timeTableRepository.uspIdentifyDuplicateTimeTable(request.getFromTime(), request.getToTime(), request.getDay()
                                                                                                                      , optonaldepartmentEntity.get().getId(),optionalSubjectEntity.get().getSem());
                if (optionalTimeTableEntity.isEmpty() && optonaldepartmentEntity.isPresent() && staffEntity != null && optionalSubjectEntity.isPresent()) {
                    TimeTableEntity timeTableEntity = TimeTableEntity.build(0l, request.getFromTime(), request.getToTime(), request.getDay(), staffEntity.getStaffId(), optionalSubjectEntity.get().getSubjectPK(), optonaldepartmentEntity.get().getId(),
                            optionalSubjectEntity.get().getSem());
                    timeTableEntity = timeTableRepository.save(timeTableEntity);
                    request.setTimeTableId(timeTableEntity.getTimeTablePK());
                    return request;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    @Override
    public TimeTableDAO update(TimeTableDAO request, long TimeTablePK) {
        try {
            if (TimeTablePK != 0l) {
                Optional<TimeTableEntity> optionalTimeTableEntity = timeTableRepository.findById(TimeTablePK);
                if (optionalTimeTableEntity.isPresent()) {
                    TimeTableEntity timeTableEntity = optionalTimeTableEntity.get();
                    Optional<TimeTableEntity> duplicateCheck = timeTableRepository.uspIdentifyDuplicateTimeTableSubject(request.getFromTime(), request.getToTime(), request.getDay()
                            , timeTableEntity.getDeptFK(),timeTableEntity.getSem(), timeTableEntity.getSubjectFK());

                    if(duplicateCheck.isPresent())
                    {
                        return null;
                    }
                    if (request.getFromTime() != null) {
                        timeTableEntity.setFromTime(request.getFromTime());
                    }
                    if (request.getToTime() != null) {
                        timeTableEntity.setToTime(request.getToTime());
                    }
                    if (request.getDay() != null) {
                        timeTableEntity.setDay(request.getDay());
                    }
                    if (request.getSubjectName() != null) {
                        Optional<SubjectEntity> optionalSubjectEntity = subjectRepository.findBysubjectName(request.getSubjectName());
                        if (optionalSubjectEntity.isPresent()) {
                            timeTableEntity.setSubjectFK(optionalSubjectEntity.get().getSubjectPK());
                        }
                    }
                    if (request.getStaffName() != null) {
                        StaffEntity staffEntity = staffRepository.findByName(request.getStaffName());
                        if (staffEntity != null) {
                            timeTableEntity.setStaffFK(staffEntity.getStaffId());
                        }
                    }
                    timeTableEntity = timeTableRepository.saveAndFlush(timeTableEntity);
                    return TimeTableDAO.build(timeTableEntity.getTimeTablePK(), timeTableEntity.getFromTime(), timeTableEntity.getToTime(),
                            timeTableEntity.getDay(), staffRepository.findById(timeTableEntity.getStaffFK()).get().getName(),
                            subjectRepository.findById(timeTableEntity.getSubjectFK()).get().getSubjectName(), departmentRepository.findById(timeTableEntity.getDeptFK()).get().getDepartmentName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TimeTableDAO> getTimeTableBySemAndDept(String departmentName, int sem) {
        try {
            if (departmentName != null && sem != 0) {
                Optional<DepartmentEntity> optonaldepartmentEntity = departmentRepository.findBydepartmentName(departmentName);
                if (optonaldepartmentEntity.isPresent()) {
                    Optional<List<TimeTableEntity>> optionalTimeTableEntity =
                            timeTableRepository.findBydeptFKAndSem(optonaldepartmentEntity.get().getId(), sem);
                    if (optionalTimeTableEntity.isPresent()) {
                        List<TimeTableEntity> listTimeTableEntity = optionalTimeTableEntity.get();
                        List<TimeTableDAO> timeTableDAOS = new ArrayList<TimeTableDAO>();
                        for (TimeTableEntity timeTableEntity : listTimeTableEntity) {
                            timeTableDAOS.add(TimeTableDAO.build(timeTableEntity.getTimeTablePK(), timeTableEntity.getFromTime(), timeTableEntity.getToTime(),
                                    timeTableEntity.getDay(), staffRepository.findById(timeTableEntity.getStaffFK()).get().getName(), subjectRepository.findById(timeTableEntity.getSubjectFK()).get().getSubjectName(),
                                    departmentName));
                        }
                        return timeTableDAOS;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteTimeTable(long TimeTablePK) {
        if (TimeTablePK != 0l) {
            Optional<TimeTableEntity> optionalTimeTableEntity = timeTableRepository.findById(TimeTablePK);
            if (optionalTimeTableEntity.isPresent()) {
                timeTableRepository.delete(optionalTimeTableEntity.get());
                return true;
            }
        }
        return false;
    }
}
