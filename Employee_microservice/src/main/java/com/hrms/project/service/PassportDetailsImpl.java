package com.hrms.project.service;

import com.hrms.project.entity.Employee;
import com.hrms.project.entity.PanDetails;
import com.hrms.project.entity.PassportDetails;
import com.hrms.project.handlers.APIException;
import com.hrms.project.handlers.EmployeeNotFoundException;
import com.hrms.project.payload.PanDTO;
import com.hrms.project.payload.PassportDetailsDTO;
import com.hrms.project.repository.EmployeeRepository;
import com.hrms.project.repository.PassportDetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassportDetailsImpl {

    @Autowired
    private PassportDetailsRepository passportDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    public PassportDetailsDTO createPassport(String employeeId, PassportDetails passportDetails) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

        if (passportDetailsRepository.findByEmployee_EmployeeId(employeeId).isPresent()) {
            throw new APIException("This employee already has a passport assigned");
        }
        Optional<PassportDetails> existingPassport = passportDetailsRepository.findById(passportDetails.getPassportNumber());

        if (existingPassport.isPresent()) {

            PassportDetails details = existingPassport.get();
            if (details.getEmployee() == null) {
                details.setEmployee(employee);
                return modelMapper.map(passportDetailsRepository.save(details), PassportDetailsDTO.class);
            } else {
                throw new APIException("This Passport is already assigned to another employee");
            }
        }

        passportDetails.setEmployee(employee);
        return modelMapper.map(passportDetailsRepository.save(passportDetails), PassportDetailsDTO.class);
    }

}
