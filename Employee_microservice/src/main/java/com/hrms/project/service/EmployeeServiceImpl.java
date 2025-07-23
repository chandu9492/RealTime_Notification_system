package com.hrms.project.service;
import com.hrms.project.entity.Department;
import com.hrms.project.entity.Employee;
import com.hrms.project.entity.Team;
import com.hrms.project.handlers.APIException;
import com.hrms.project.handlers.DepartmentNotFoundException;
import com.hrms.project.handlers.EmployeeNotFoundException;
import com.hrms.project.payload.*;
import com.hrms.project.repository.DepartmentRepository;
import com.hrms.project.repository.EmployeeRepository;
import com.hrms.project.repository.ProjectRepository;
import com.hrms.project.repository.TeamRepository;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;


@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;


    @Override
    public EmployeeDTO createData(MultipartFile image, EmployeeDTO employeeDTO) throws IOException {

        if (employeeRepository.findById(employeeDTO.getEmployeeId()).isPresent()) {
            throw new APIException("Employee already exists with id " + employeeDTO.getEmployeeId());
        }

        Employee employee = new Employee();

        modelMapper.map(employeeDTO, employee);

        if (image != null && !image.isEmpty()) {
            String employeeImage = fileService.uploadImage(path, image);
            employee.setEmployeeImage(employeeImage);
        }

        if (employeeDTO.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with name: " + employeeDTO.getDepartmentId()));
            employee.setDepartment(dept);
        }

        employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeDTO.class);


//        if (employeeDTO.getHeadOfDepartment() != null && !employeeDTO.getHeadOfDepartment().isEmpty()) {
//            Department dept = departmentRepository.findByDepartmentName(employeeDTO.getDepartmentName())
//                    .orElseThrow(()->new DepartmentNotFoundException("Department not found with name: " + employeeDTO.getDepartmentName()));
//            dept.setHeadOfDepartment(employeeDTO.getHeadOfDepartment());
//            employee.setDepartment(dept);
//        }

    }


    @Override
    public EmployeeDTO getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

//    List<String> projectNames = employee.getProjects()
//            .stream()
//            .map(Project -> Project.getTitle())
//                    .toList();
//
//        employeeDTO.setProjects(projectNames);

        return employeeDTO;

    }


    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> allEmployeeDTOs = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

//            List<String> projectNames = employee.getProjects()
//                    .stream()
//                    .map(Project-> Project.getTitle())
//                    .toList();
//
//            employeeDTO.setProjectNames(projectNames);

            allEmployeeDTOs.add(employeeDTO);
        }

        return allEmployeeDTOs;
    }


    @Override
    public EmployeeDTO updateEmployee(String id, MultipartFile image, EmployeeDTO employeeDTO) throws IOException {

        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        if (image != null && !image.isEmpty()) {
            String fileName = fileService.uploadImage(path, image);
            updateEmployee.setEmployeeImage(fileName);
        }
        modelMapper.map(employeeDTO, updateEmployee);
        Employee  updatedEmployeeDetails=employeeRepository.save(updateEmployee);


        return modelMapper.map(updatedEmployeeDetails, EmployeeDTO.class);
    }

    @Override
    public ContactDetailsDTO getEmployeeContactDetails(String employeeId) {

        Employee employeeDetails=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));

        ContactDetailsDTO contactDetailsDTO = modelMapper.map(employeeDetails, ContactDetailsDTO.class);
        return contactDetailsDTO;

    }

    @Override
    public List<ContactDetailsDTO> getAllEmployeeContactDetails()
    {
        List<Employee> allEmployeeDetails=employeeRepository.findAll();
        return allEmployeeDetails.stream()
                .map(employeeDetails-> modelMapper.map(employeeDetails,ContactDetailsDTO.class))
                .toList();
    }

    @Override
    public ContactDetailsDTO updateContactDetails(String employeeId,ContactDetailsDTO contactDetailsDTO) {
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));

        modelMapper.map(employee,contactDetailsDTO);
        employeeRepository.save(employee);
        return modelMapper.map(employee, ContactDetailsDTO.class);
    }

    @Override
    public AddressDTO getAddress(String employeeId) {
       Employee employee=employeeRepository.findById(employeeId)
               .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
       return modelMapper.map(employee, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllAddress() {
        List<Employee> allEmployee=employeeRepository.findAll();
       return allEmployee.stream()
                .map(employeeDetails-> modelMapper.map(employeeDetails, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO updateEmployeeAddress(String employeeId, AddressDTO addressDTO) {
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));

        modelMapper.map(employee,addressDTO);
        employeeRepository.save(employee);
        return modelMapper.map(employee, AddressDTO.class);
    }

    @Override
    public EmployeePrimaryDetailsDTO getEmployeePrimaryDetails(String employeeId) {
       Employee employeeDetails=employeeRepository.findById(employeeId)
               .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
       return modelMapper.map(employeeDetails, EmployeePrimaryDetailsDTO.class);
    }

    @Override
    public EmployeePrimaryDetailsDTO updateEmployeeDetails(String employeeId, EmployeePrimaryDetailsDTO employeePrimaryDetailsDTO) {
        Employee employeeDetails=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
        modelMapper.map(employeeDetails,employeePrimaryDetailsDTO);
        employeeRepository.save(employeeDetails);
        return modelMapper.map(employeeDetails, EmployeePrimaryDetailsDTO.class);

    }

    @Override
    public JobDetailsDTO getJobDetails(String employeeId) {
      Employee employee=employeeRepository.findById(employeeId)
              .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
       return modelMapper.map(employee, JobDetailsDTO.class);
    }

    @Override
    public JobDetailsDTO updateJobDetails(String employeeId, JobDetailsDTO jobDetailsDTO) {
       Employee employee=employeeRepository.findById(employeeId)
               .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
       modelMapper.map(employee,jobDetailsDTO);
        employeeRepository.save(employee);
        return modelMapper.map(employee, JobDetailsDTO.class);
    }


    @Override
    public EmployeeDTO deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("employee not found with id: " + employeeId));

        for (Team team : employee.getTeams()) {
            team.getEmployees().remove(employee);
        }

        employee.getTeams().clear();

        employeeRepository.delete(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

}

















