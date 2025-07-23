package com.hrms.project.service;


import com.hrms.project.payload.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Component
public interface EmployeeService {

    EmployeeDTO createData(MultipartFile image, EmployeeDTO employeeDTO) throws IOException;

    EmployeeDTO getEmployeeById(String id);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO deleteEmployee(String id);

    EmployeeDTO updateEmployee(String id, MultipartFile image ,EmployeeDTO employeeDTO) throws IOException;

    ContactDetailsDTO getEmployeeContactDetails(String employeeId);

    List<ContactDetailsDTO> getAllEmployeeContactDetails();

    ContactDetailsDTO updateContactDetails(String employeeId,ContactDetailsDTO contactDetailsDTO);

    AddressDTO getAddress(String employeeId);

    List<AddressDTO> getAllAddress();

    AddressDTO updateEmployeeAddress(String employeeId, AddressDTO addressDTO);

    EmployeePrimaryDetailsDTO getEmployeePrimaryDetails(String employeeId);

    EmployeePrimaryDetailsDTO updateEmployeeDetails(String employeeId, EmployeePrimaryDetailsDTO employeePrimaryDetailsDTO);

    JobDetailsDTO getJobDetails(String employeeId);

    JobDetailsDTO updateJobDetails(String employeeId, JobDetailsDTO jobDetailsDTO);

}