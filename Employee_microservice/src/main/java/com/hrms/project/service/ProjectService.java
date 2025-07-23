package com.hrms.project.service;



import com.hrms.project.payload.ProjectDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);

    List<ProjectDTO> getAllProjects();

    ProjectDTO getProjectById(String id);

    ProjectDTO updateProject(String id, ProjectDTO projectDTO);

    ProjectDTO deleteProject(String id);
}
