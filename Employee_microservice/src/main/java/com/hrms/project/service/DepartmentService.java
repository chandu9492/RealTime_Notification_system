package com.hrms.project.service;

import com.hrms.project.entity.Department;
import com.hrms.project.payload.DepartmentDTO;
import com.hrms.project.payload.EmployeeDepartmentDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface DepartmentService {


    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);

    EmployeeDepartmentDTO getEmployeesByDepartmentId(String departmentId);

    DepartmentDTO updateDepartment(String departmentId, DepartmentDTO departmentDTO);

    List<DepartmentDTO> getAllDepartmentDetails();

    DepartmentDTO getByDepartmentId(String departmentId);
}
