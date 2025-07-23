package com.hrms.project.repository;


import com.hrms.project.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {


//    List<Employee> findByEmployeeRole(String role);
//
//
//
//    List<Employee> findAllByDepartment_DepartmentId(String departmentId);
//
//
//    Employee findByEmployeeName(String name);
}
