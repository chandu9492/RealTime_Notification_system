package com.hrms.project.controller;

import com.hrms.project.entity.AadhaarCardDetails;
import com.hrms.project.entity.DrivingLicense;
import com.hrms.project.entity.PanDetails;
import com.hrms.project.entity.PassportDetails;
import com.hrms.project.payload.DrivingLicenseDTO;
import com.hrms.project.payload.PanDTO;
import com.hrms.project.payload.PassportDetailsDTO;
import com.hrms.project.service.AadhaarServiceImpl;
import com.hrms.project.service.DrivingLicenseServiceImpl;
import com.hrms.project.service.PanServiceImpl;
import com.hrms.project.service.PassportDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IdentityDetailsController {

    @Autowired
    private AadhaarServiceImpl aadhaarServiceImpl;

    @Autowired
    private PanServiceImpl panServiceImpl;

    @Autowired
    private DrivingLicenseServiceImpl drivingLicenseServiceImpl;

    @Autowired
    private PassportDetailsImpl passportDetailsImpl;

@PostMapping("/aadhaar/{employeeId}")
    public ResponseEntity<AadhaarCardDetails> save(@PathVariable String employeeId,
                                                   @RequestBody AadhaarCardDetails aadhaarCardDetails) {

    return new ResponseEntity<>(aadhaarServiceImpl.createAadhaar(employeeId,aadhaarCardDetails), HttpStatus.CREATED);
}

    @GetMapping("/{employeeId}/aadhaar")
    public ResponseEntity<AadhaarCardDetails>getAadhaar(@PathVariable String employeeId){
        AadhaarCardDetails aadhaarDTO=aadhaarServiceImpl.getAadhaarByEmployeeId(employeeId);
        return new ResponseEntity<>(aadhaarDTO, HttpStatus.OK);

    }
    @PutMapping("/{employeeId}/aadhaar")
    public ResponseEntity<AadhaarCardDetails>updateAadhaar(@PathVariable String employeeId, @RequestBody AadhaarCardDetails aadhaarDTO){
        AadhaarCardDetails dto = aadhaarServiceImpl.updateAadhaar(employeeId,aadhaarDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

@PostMapping("/pan/{employeeId}")
public ResponseEntity<PanDTO> savePan(@PathVariable String employeeId, @RequestBody PanDetails panDetails) {
    return new ResponseEntity<>(panServiceImpl.createPan(employeeId,panDetails),HttpStatus.CREATED);
}

@PostMapping("/driving/license/{employeeId}")
    public ResponseEntity<DrivingLicenseDTO> saveLicense(@PathVariable String employeeId,
                                                  @RequestBody DrivingLicense drivingLicense) {

    return new ResponseEntity<>(drivingLicenseServiceImpl.createDrivingLicense(employeeId,drivingLicense),HttpStatus.CREATED);

}


@PostMapping("/passport/details/{employeeId}")
public ResponseEntity<PassportDetailsDTO> savePassportDetails(@PathVariable String employeeId,
                                                              @RequestBody PassportDetails passportDetails) {
    return new ResponseEntity<>(passportDetailsImpl.createPassport(employeeId,passportDetails),HttpStatus.CREATED);
}
}


