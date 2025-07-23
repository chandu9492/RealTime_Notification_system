package com.hrms.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTeamResponse {


    private String employeeId;
    private String displayName;
    private String jobTitlePrimary;
    private String workEmail;
    private String workNumber;
}
