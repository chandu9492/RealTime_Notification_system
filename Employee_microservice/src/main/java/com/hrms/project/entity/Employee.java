package com.hrms.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"teams", "projects", "department", "panDetails", "drivingLicense", "passportDetails"})
@ToString(exclude = {"teams", "projects", "department", "panDetails", "drivingLicense", "passportDetails"})
@Entity
public class Employee {

    @Id
    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String displayName;
    private String maritalStatus;
    private String bloodGroup;
    private String physicallyHandicapped;
    private String nationality;
    private String jobTitlePrimary;
    private String jobTitleSecondary;
    private String gender;
    private String workEmail;
    private String personalEmail;
    private String mobileNumber;
    private String workNumber;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String district;

    private String employeeImage;

    private LocalDate dateOfBirth;
    private LocalDate dateOfJoining;
    private String inProbation;
    private LocalDate probationStartDate;
    private LocalDate probationEndDate;
    private String probationPolicy;
    private String workingType;
    private String timeType;
    private String contractStatus;
    private LocalDate contractStartDate;


    @ManyToMany(mappedBy = "employees")
    private List<Team> teams = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_Id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )

    private List<Project> projects = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "departmentId")
    @JsonBackReference
    private Department department;

//    @OneToOne(mappedBy = "employee")
//    private AadhaarCardDetails aadhaarCardDetails;
//
    @OneToOne(mappedBy = "employee")
    @JsonManagedReference
    private PanDetails panDetails;

    @OneToOne(mappedBy = "employee")
    @JsonManagedReference
    private DrivingLicense drivingLicense;

    @OneToOne(mappedBy = "employee")
    @JsonManagedReference
    private PassportDetails passportDetails;



}




