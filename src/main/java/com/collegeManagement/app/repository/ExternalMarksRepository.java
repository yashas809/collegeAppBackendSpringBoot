package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.ExternalMarksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExternalMarksRepository extends JpaRepository<ExternalMarksEntity, Long> {

    public Optional<ExternalMarksEntity> findByusnAndSubjectFKAndSemAndDeptFK(String usn, long subjectFK, int sem, long deptFK);

    public Optional<List<ExternalMarksEntity>> findByUsnAndSem(String usn, int sem);

    public Optional<List<ExternalMarksEntity>> findBysem(int sem);

    public Optional<List<ExternalMarksEntity>> findByDeptFK(long deptFK);
}
