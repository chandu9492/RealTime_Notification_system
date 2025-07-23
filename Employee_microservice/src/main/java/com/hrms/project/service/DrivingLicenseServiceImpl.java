package com.hrms.project.service;

import com.hrms.project.entity.DrivingLicense;
import com.hrms.project.entity.Employee;
import com.hrms.project.entity.PanDetails;
import com.hrms.project.handlers.APIException;
import com.hrms.project.handlers.EmployeeNotFoundException;
import com.hrms.project.payload.DrivingLicenseDTO;
import com.hrms.project.payload.PanDTO;
import com.hrms.project.repository.DrivingLicenseRepository;
import com.hrms.project.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DrivingLicenseServiceImpl {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DrivingLicenseRepository drivingLicenseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DrivingLicenseDTO createDrivingLicense(String employeeId,
                                                 DrivingLicense drivingLicense) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

        if (drivingLicenseRepository.findByEmployee_EmployeeId(employeeId).isPresent()) {
            throw new APIException("This employee already has a License assigned");
        }
        Optional<DrivingLicense> existingLicense = drivingLicenseRepository.findById(drivingLicense.getLicenseNumber());

        if (existingLicense.isPresent()) {

           DrivingLicense license = existingLicense.get();
            if (license.getEmployee() == null) {
                license.setEmployee(employee);
                return modelMapper.map(drivingLicenseRepository.save(license), DrivingLicenseDTO.class);
            } else {
                throw new APIException("This License is already assigned to another employee");
            }
        }

        drivingLicense.setEmployee(employee);
        return modelMapper.map(drivingLicenseRepository.save(drivingLicense), DrivingLicenseDTO.class);
    }

}

