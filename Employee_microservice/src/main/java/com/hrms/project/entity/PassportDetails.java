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
public class PassportDetails {

    @Id
    private String passportNumber;
    private String countryCode;
    private String passportType;
    private LocalDate dateOfBirth;
    private String name;
    private String gender;
    private LocalDate dateOfIssue;
    private String placeOfIssue;
    private String placeOfBirth;
    private LocalDate dateOfExpiration;

    @OneToOne
 @JoinColumn(name="employee_id")
    @JsonBackReference
    private Employee employee;
}
