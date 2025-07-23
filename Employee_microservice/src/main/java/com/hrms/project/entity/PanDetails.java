package com.hrms.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"employee"})
@ToString(exclude = {"employee"})
@Entity
public class PanDetails {
    @Id
    private String panNumber;
    private String panName;
    private String DateOfBirth;
    private String ParentsName;
    //private String imagePath;

    @OneToOne
    @JoinColumn(name="employee_id")
    @JsonBackReference
    private Employee employee;

}
