package com.hrms.project.repository;

import com.hrms.project.entity.PassportDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PassportDetailsRepository extends CrudRepository<PassportDetails, String> {
   Optional<PassportDetails> findByEmployee_EmployeeId(String employeeId) ;

}
