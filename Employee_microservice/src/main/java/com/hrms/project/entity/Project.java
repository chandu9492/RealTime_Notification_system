package com.hrms.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id

    private String projectId;
    private String title;
    private String client;
    private String description;
    private String projectPriority;
    private String projectStatus;
    private LocalDate startDate;
    private LocalDate endDate;



    @ManyToOne

    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees;


}
