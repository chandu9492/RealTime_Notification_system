package com.hrms.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanDTO {

    private String panNumber;
    private String panName;
    private String dateOfBirth;
    private String parentsName;
    private String employeeId;
  //  private String EmployeeName;
}
