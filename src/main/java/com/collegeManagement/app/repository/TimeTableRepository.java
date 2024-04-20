package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.InternalMarksEntity;
import com.collegeManagement.app.entity.TimeTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeTableRepository extends JpaRepository<TimeTableEntity, Long>
{
    public Optional<List<TimeTableEntity>> findBydeptFKAndSem(long deptFK, int sem);

    @Procedure(name = "uspIdentifyDuplicateTimeTable")
    public Optional<TimeTableEntity> uspIdentifyDuplicateTimeTable(@Param("fromtime") LocalTime fromtime,
                                                                             @Param("toTime") LocalTime totime,
                                                                             @Param("_day") String _day,
                                                                             @Param("deptFK") long deptFK,
                                                                             @Param("sem") long sem
                                                                             );
    @Procedure(name = "uspIdentifyDuplicateTimeTableSubject")
    public Optional<TimeTableEntity> uspIdentifyDuplicateTimeTableSubject(@Param("from_time") LocalTime fromtime,
                                                                   @Param("to_time") LocalTime totime,
                                                                   @Param("_day") String _day,
                                                                   @Param("dept_fk") long deptFK,
                                                                   @Param("sem") long sem,
                                                                   @Param("subject_fk") long subjectFK
    );

}
