package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.InternalMarksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InternalMarksRepository extends JpaRepository<InternalMarksEntity, Long> {

    public Optional<InternalMarksEntity> findByusnAndSubjectFK(String usn, long subjectFK);

    public Optional<List<InternalMarksEntity>> findByusn(String usn);
}
