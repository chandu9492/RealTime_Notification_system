package com.hrms.project.repository;

import com.hrms.project.entity.DrivingLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrivingLicenseRepository extends JpaRepository<DrivingLicense, String> {

    Optional<DrivingLicense> findByEmployee_EmployeeId(String employeeId);
}
