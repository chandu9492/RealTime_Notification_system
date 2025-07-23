package com.hrms.project.controller;


import com.hrms.project.payload.*;
import com.hrms.project.service.EmployeeService;
import com.hrms.project.service.FileService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> createEmployees(@RequestPart(value = "image", required = false) MultipartFile image,
                                                       @Valid @RequestPart(value = "employee") EmployeeDTO employeeDTO) throws IOException {

        EmployeeDTO saved = employeeService.createData(image, employeeDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @GetMapping("/employee/{employeeId}/contact")
    public ResponseEntity<ContactDetailsDTO> getContactDetails(@PathVariable String employeeId) {
        ContactDetailsDTO contactDetailsDTO = employeeService.getEmployeeContactDetails(employeeId);
        return new ResponseEntity<>(contactDetailsDTO, HttpStatus.OK);
    }

    @GetMapping("/all/employee/contact")
    public ResponseEntity<List<ContactDetailsDTO>> getAllContactDetails() {
        List<ContactDetailsDTO> contactDetailsDTO = employeeService.getAllEmployeeContactDetails();
        return new ResponseEntity<>(contactDetailsDTO, HttpStatus.OK);
    }

    @PutMapping("/employee/{employeeId}/contact")
    public ResponseEntity<ContactDetailsDTO> updateContactDetails(@PathVariable String employeeId,
                                                                  @RequestBody ContactDetailsDTO contactDetailsDTO)
    {
        ContactDetailsDTO updatedContactDetails = employeeService.updateContactDetails(employeeId,contactDetailsDTO);
        return new ResponseEntity<>(updatedContactDetails, HttpStatus.CREATED);
    }

    @GetMapping("/employee/{employeeId}/address")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable String employeeId) {

        AddressDTO addressDTO=employeeService.getAddress(employeeId);
        return new ResponseEntity<>(addressDTO,HttpStatus.OK);

    }

    @GetMapping("/all/employee/address")
    public ResponseEntity<List<AddressDTO>> getAllAddress() {
        List<AddressDTO> addressDTOList=employeeService.getAllAddress();
        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
    }

    @PutMapping("/employee/{EmployeeId}/address")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable String employeeId
            ,@RequestBody AddressDTO addressDTO)
    {
        AddressDTO updatedAddressDTO=employeeService.updateEmployeeAddress(employeeId,addressDTO);
        return new ResponseEntity<>(updatedAddressDTO,HttpStatus.CREATED);
    }


    @GetMapping("/employee/{employeeId}/primary/details")
    public ResponseEntity<EmployeePrimaryDetailsDTO> getEmployeePrimaryDetails(@PathVariable String employeeId) {
        EmployeePrimaryDetailsDTO primaryDetails=employeeService.getEmployeePrimaryDetails(employeeId);
        return new ResponseEntity<>(primaryDetails, HttpStatus.OK);
    }

    @PutMapping("/employee/{employeeId}/primary/details")
    public ResponseEntity<EmployeePrimaryDetailsDTO> updateEmployeePrimaryDetails(@PathVariable String employeeId
            ,@RequestBody EmployeePrimaryDetailsDTO employeePrimaryDetailsDTO)
    {
        EmployeePrimaryDetailsDTO updatedPrimaryDetails=employeeService.updateEmployeeDetails(employeeId,employeePrimaryDetailsDTO);
        return new ResponseEntity<>(updatedPrimaryDetails, HttpStatus.CREATED);
    }


    @GetMapping("/employee/{employeeId}/job/details")
    public ResponseEntity<JobDetailsDTO> getJobDetails(@PathVariable String employeeId) {
        JobDetailsDTO jobDetailsDTO=employeeService.getJobDetails(employeeId);
        return new ResponseEntity<>(jobDetailsDTO, HttpStatus.OK);
    }

    @PutMapping("/employee/{employeeId}/job/details")
    public ResponseEntity<JobDetailsDTO> updateJobDetails(@PathVariable String employeeId
            ,@RequestBody JobDetailsDTO jobDetailsDTO)
    {
        JobDetailsDTO updatedJobDetails=employeeService.updateJobDetails(employeeId,jobDetailsDTO);
        return new ResponseEntity<>(updatedJobDetails, HttpStatus.OK);
    }


    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        EmployeeDTO employeeDetails = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeDetails, HttpStatus.OK);
    }


    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeResponse = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }


    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable String id,
            @Valid @RequestPart(value = "employee") EmployeeDTO employeeDTO) throws IOException {

        EmployeeDTO updatedEmployeeDetails = employeeService.updateEmployee(id, image, employeeDTO);
        return new ResponseEntity<>(updatedEmployeeDetails, HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable String id) {
        EmployeeDTO deletedEmployee = employeeService.deleteEmployee(id);
        return new ResponseEntity<>(deletedEmployee, HttpStatus.OK);

    }

}

