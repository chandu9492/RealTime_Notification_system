package com.hrms.project.repository;

import com.hrms.project.entity.PanDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PanDetailsRepository extends JpaRepository<PanDetails,String> {
   Optional<PanDetails> findByEmployee_EmployeeId(String employeeId);
}
