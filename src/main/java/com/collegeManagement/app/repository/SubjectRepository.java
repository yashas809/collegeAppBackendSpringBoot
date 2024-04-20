package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    public Optional<SubjectEntity> findBysubjectCode(String subjectCode);

    public Optional<SubjectEntity> findBysubjectName(String subjectName);

    public Optional<List<SubjectEntity>> findBysem(int sem);

    public Optional<List<SubjectEntity>> findBysemAndDeptFK(int sem, long deptfk);

}
