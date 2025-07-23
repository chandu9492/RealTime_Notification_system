package com.hrms.project.controller;

import com.hrms.project.payload.ProjectDTO;
import com.hrms.project.service.ProjectService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
         projectService.saveProject(projectDTO);
         return new ResponseEntity<>(projectDTO, HttpStatus.CREATED);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
      List<ProjectDTO> response=projectService.getAllProjects();
      return ResponseEntity.ok().body(response);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String id) {
        ProjectDTO projectResponse= projectService.getProjectById(id);
        return ResponseEntity.ok().body(projectResponse);
    }

    @PutMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable String id, @Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO updatedProject = projectService.updateProject(id, projectDTO);
        return new ResponseEntity<>(updatedProject, HttpStatus.CREATED);
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> deleteProject(@PathVariable String id) {
        ProjectDTO deletedProject=projectService.deleteProject(id);
        return new ResponseEntity<>(deletedProject, HttpStatus.OK);
    }
}
