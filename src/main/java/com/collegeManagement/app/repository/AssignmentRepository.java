package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity,Long>
{
    Optional<List<AssignmentEntity>> findBysubjectFK(long subjectFk);

    Optional<List<AssignmentEntity>> findByusn(String usn);

    Optional<AssignmentEntity> findByUsnAndSubjectFK(String usn,long subjectFK);
}
