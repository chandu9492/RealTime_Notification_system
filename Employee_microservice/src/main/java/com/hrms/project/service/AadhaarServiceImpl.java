package com.hrms.project.service;

import com.hrms.project.entity.AadhaarCardDetails;
import com.hrms.project.entity.Employee;
import com.hrms.project.handlers.APIException;
import com.hrms.project.handlers.EmployeeNotFoundException;
import com.hrms.project.repository.AadhaarDetailsRepository;
import com.hrms.project.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AadhaarServiceImpl {

    @Autowired
    private AadhaarDetailsRepository aadhaarDetailsRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AadhaarCardDetails createAadhaar(String employeeId,AadhaarCardDetails aadhaarCardDetails) {

        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(()->new EmployeeNotFoundException("employee not found with id: " + employeeId));

        if( aadhaarDetailsRepository.findById(aadhaarCardDetails.getAadhaarNumber()).isEmpty() ) {
            aadhaarCardDetails.setEmployee(employee);
            aadhaarDetailsRepository.save(aadhaarCardDetails);

        }
        else
        {
            AadhaarCardDetails details=aadhaarDetailsRepository.findById(aadhaarCardDetails.getAadhaarNumber()).get();
            if(details.getEmployee().getEmployeeId().isEmpty())
            {
                details.setEmployee(employee);
                aadhaarDetailsRepository.save(details);
            }
            else
            {
                throw new APIException("Current aadhaar card Details already exists with other employee");
            }
        }

        return aadhaarCardDetails;
    }

    public AadhaarCardDetails getAadhaarByEmployeeId(String employeeId) {


        Employee employee=employeeRepository.findById(employeeId).orElseThrow(() ->
                new EmployeeNotFoundException("Employee Not Found with id: " + employeeId));

        AadhaarCardDetails aadhaarCardDetails=aadhaarDetailsRepository.findByEmployee_EmployeeId(employeeId);
        if(aadhaarCardDetails==null)
        {
            throw new APIException("Aadhaar card Details not found for the employee with id: " + employeeId);
        }

        AadhaarCardDetails details=modelMapper.map(aadhaarCardDetails, AadhaarCardDetails.class);
        details.setEmployee(employee);

        return details;
    }


    public AadhaarCardDetails updateAadhaar(String employeeId,AadhaarCardDetails aadhaarCardDetails) {
        Employee employee=employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found"));

        AadhaarCardDetails existing=aadhaarDetailsRepository.findByEmployee_EmployeeId(employeeId);
        if(existing==null)
        {
            throw new APIException("Aadhaar card Details not found for employee with id: " + employeeId);
        }


        if (!existing.getAadhaarNumber().equals(aadhaarCardDetails.getAadhaarNumber())) {
            throw new APIException("Aadhaar number cannot be changed once submitted");
        }

        existing.setGender(aadhaarCardDetails.getGender());
        existing.setDateOfBirth(aadhaarCardDetails.getDateOfBirth());
        existing.setAadhaarName(aadhaarCardDetails.getAadhaarName());
        existing.setAadhaarNumber(aadhaarCardDetails.getAadhaarNumber());
        existing.setGender(aadhaarCardDetails.getGender());

        AadhaarCardDetails aadhaarDetails=aadhaarDetailsRepository.save(existing);

        return aadhaarDetails;
    }


}
