package com.hrms.project.repository;


import com.hrms.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
     // Optional<Project> findByTitle(String projectTitle);


}
