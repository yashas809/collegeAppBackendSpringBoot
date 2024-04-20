package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.AssignmentEntity;
import com.collegeManagement.app.entity.TimeTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity,Long>
{
    Optional<List<AssignmentEntity>> findBysubjectFK(long subjectFk);

    Optional<List<AssignmentEntity>> findByusn(String usn);

    Optional<AssignmentEntity> findByUsnAndSubjectFK(String usn,long subjectFK);

    @Procedure(name = "uspgetAssignment")
    public Optional<List<AssignmentEntity>> uspgetAssignment(@Param("dept_fk") String deptFK,@Param("_sem") long sem);

    @Procedure(name = "uspgetAssignmentByUSNandSem")
    public Optional<List<AssignmentEntity>> uspgetAssignmentByUSNandSem(@Param("USN") String usn,@Param("_sem") long sem);
}
