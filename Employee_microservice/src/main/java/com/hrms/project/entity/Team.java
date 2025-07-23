package com.hrms.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Team {

    @Id
    private String teamId;

    private String teamName;

    private String teamDescription;

    @ManyToMany
    @JoinTable(
            name = "team_employee",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )

    private Set<Employee> employees = new HashSet<>();


    @OneToMany(mappedBy = "team")

    private List<Project> projects = new ArrayList<>();

}
