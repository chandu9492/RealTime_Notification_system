package com.hrms.project.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Department {

    @Id

    private String departmentId;
    private String departmentName;

    private String departmentDescription;
   // private String headOfDepartment;
    //private Long employeeCapacity;
   // private Long departmentEstablishedYear;
    //private Long totalEmployees;

    @OneToMany(mappedBy = "department")
    @JsonManagedReference
    private List<Employee> employee;
}


