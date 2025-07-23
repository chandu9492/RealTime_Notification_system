package com.hrms.project.service;

import com.hrms.project.entity.Employee;
import com.hrms.project.entity.PanDetails;
import com.hrms.project.handlers.APIException;
import com.hrms.project.handlers.EmployeeNotFoundException;
import com.hrms.project.payload.PanDTO;
import com.hrms.project.repository.EmployeeRepository;
import com.hrms.project.repository.PanDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PanServiceImpl {
    @Autowired
    private PanDetailsRepository panDetailsRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public PanDTO createPan(String employeeId, PanDetails panDetails) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

        if (panDetailsRepository.findByEmployee_EmployeeId(employeeId).isPresent()) {
            throw new APIException("This employee already has a PAN assigned");
        }
        Optional<PanDetails> existingPan = panDetailsRepository.findById(panDetails.getPanNumber());

        if (existingPan.isPresent()) {

            PanDetails pan = existingPan.get();
            if (pan.getEmployee() == null) {
                pan.setEmployee(employee);
                return modelMapper.map(panDetailsRepository.save(pan), PanDTO.class);
            } else {
                throw new APIException("This PAN is already assigned to another employee");
            }
        }

        panDetails.setEmployee(employee);
        return modelMapper.map(panDetailsRepository.save(panDetails), PanDTO.class);
    }


}

