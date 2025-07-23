package com.hrms.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePrimaryDetailsDTO {

    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String displayName;
    private String maritalStatus;
    private String bloodGroup;
    private String physicallyHandicapped;
    private String nationality;
    private String gender;
    private LocalDate DOB;
    private String employeeImage;

}
