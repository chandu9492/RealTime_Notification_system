package com.hrms.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"employee"})
@ToString(exclude = {"employee"})
@Entity
public class DrivingLicense {

    @Id
    private String licenseNumber;
    private String name;
    private String dateOfBirth;
    private String bloodGroup;
    private String fatherName;
    private LocalDate issueDate;
    private LocalDate expiresOn;
    private String address;

    @OneToOne
    @JoinColumn(name="employee_id")
    @JsonBackReference
    private Employee employee;



}
