package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.FeeStructureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeeStructureRepository extends JpaRepository<FeeStructureEntity, Long>
{
    List<FeeStructureEntity> findBydeptFK(long deptFK);

    Optional<FeeStructureEntity> findBydeptFKAndSem(long deptFK, int Sem);
}
