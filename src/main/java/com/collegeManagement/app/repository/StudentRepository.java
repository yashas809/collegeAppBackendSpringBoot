package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByusn(String usn);

    Optional<List<StudentEntity>> findBySem(int Sem);


}
