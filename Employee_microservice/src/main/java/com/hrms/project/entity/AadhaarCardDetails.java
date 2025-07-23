package com.hrms.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AadhaarCardDetails {

    @Id
    private String aadhaarNumber;
    private String enrollmentNumber;
    private LocalDate dateOfBirth;
    private String aadhaarName;
    private String address;
    private String gender;
    private String uploadAadhaar;


    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    @JsonBackReference
    private Employee employee;


}