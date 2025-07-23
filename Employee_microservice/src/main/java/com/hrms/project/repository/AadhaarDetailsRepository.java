package com.hrms.project.repository;

import com.hrms.project.entity.AadhaarCardDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AadhaarDetailsRepository extends JpaRepository<AadhaarCardDetails, String> {

    AadhaarCardDetails findByEmployee_EmployeeId(String employeeId);
}
