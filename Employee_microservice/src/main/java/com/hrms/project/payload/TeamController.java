package com.hrms.project.payload;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamController {
    @Pattern(regexp="^[TEAM]\\d{3}",message = "Team id must start with TEAM followed by 3 digits")
    private String teamId;
    private String teamName;
    private String teamDescription;
    private List<String> employeeIds;
    private String projectId;

}
