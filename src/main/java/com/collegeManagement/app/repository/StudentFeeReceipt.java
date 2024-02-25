package com.collegeManagement.app.repository;

import com.collegeManagement.app.entity.FeeReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentFeeReceipt extends JpaRepository<FeeReceipt, Long> {
}
