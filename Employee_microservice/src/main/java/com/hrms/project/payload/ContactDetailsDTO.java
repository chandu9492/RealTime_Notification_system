package com.hrms.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailsDTO {

    private String workEmail;
    private String personalEmail;
    private String mobileNumber;
    private String workNumber;
}
