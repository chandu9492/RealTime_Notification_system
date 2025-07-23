package com.hrms.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrivingLicenseDTO {

    private String licenseNumber;
    private String name;
    private String dateOfBirth;
    private String bloodGroup;
    private String fatherName;
    private LocalDate issueDate;
    private LocalDate expiresOn;
    private String address;
    private String employeeId;
}
