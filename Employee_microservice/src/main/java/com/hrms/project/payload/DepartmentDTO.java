package com.hrms.project.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    @NotBlank(message="DepartmentId is required")
    @Pattern(regexp = "^DEP\\d{3}$",message = "departmentId  should start with DEP followed by 3 digits")
    private String departmentId;
    private String departmentName;
    private String departmentDescription;
}
