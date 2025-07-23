package com.hrms.project.service;

//import org.hrm.Payloads.ProjectDTO;
//import org.hrm.entity.Department;
//import org.hrm.entity.Employee;
//import org.hrm.entity.Project;
//import org.hrm.handlers.APIException;
//import org.hrm.handlers.DepartmentNotFoundException;
//import org.hrm.handlers.ProjectNotFoundException;
//import org.hrm.handlers.TeamNotFoundException;
//import org.hrm.repository.DepartmentRepository;
//import org.hrm.repository.EmployeeRepository;
//import org.hrm.repository.ProjectRepository;
//import org.hrm.repository.TeamRepository;
import com.hrms.project.entity.Employee;
import com.hrms.project.entity.Project;
import com.hrms.project.handlers.APIException;
import com.hrms.project.handlers.ProjectNotFoundException;
import com.hrms.project.payload.ProjectDTO;
import com.hrms.project.repository.DepartmentRepository;
import com.hrms.project.repository.EmployeeRepository;
import com.hrms.project.repository.ProjectRepository;
import com.hrms.project.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        if(projectRepository.findById(projectDTO.getProjectId()).isPresent())
        {
            throw new APIException("Project already exists with ID "+projectDTO.getProjectId());
        }

        Project project = new Project();

        project.setProjectId(projectDTO.getProjectId());
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setProjectStatus(projectDTO.getProjectStatus());
        project.setProjectPriority(projectDTO.getProjectPriority());
        project.setClient(projectDTO.getClient());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());


//
//        List<Team> teams = new ArrayList<>();
//        for (String teamName : projectDTO.getTeamName()) {
//
//            Team team = teamRepository.findByTeamName(teamName)
//                    .orElseThrow(() -> new TeamNotFoundException("Team not found with name: " +teamName ));
//            teams.add(team);
//        }
//        project.setTeams(teams);


//        List<Department> departments = new ArrayList<>();
//        for(String department: projectDTO.getDepartment()) {
//            Department departmentDetails = departmentRepository.findByDepartmentName(department)
//                    .orElseThrow(()-> new DepartmentNotFoundException("Department not found with name: " + department));
//            departments.add(departmentDetails);
//
//        }
//        project.setDepartments(departments);


        projectRepository.save(project);

        return projectDTO;
    }


    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        List<ProjectDTO> responseList=new ArrayList<>();

        for (Project project : projects) {
            ProjectDTO response=new ProjectDTO();

            response.setProjectId(project.getProjectId());
            response.setTitle(project.getTitle());
            response.setClient(project.getClient());
            response.setDescription(project.getDescription());
            response.setProjectStatus(project.getProjectStatus());
            response.setStartDate(project.getStartDate());
            response.setEndDate(project.getEndDate());
            response.setProjectPriority(project.getProjectPriority());

          //  response.setTeamName(project.getTeam().getTeamName());
            responseList.add(response);

        }


        return responseList;


    }



    @Override
    public ProjectDTO getProjectById(String id) {
        Project project=projectRepository.findById(id)
                .orElseThrow(()->new ProjectNotFoundException("Project not found with id "+id));

        ProjectDTO response=new ProjectDTO();
        response.setProjectId(project.getProjectId());
        response.setTitle(project.getTitle());
        response.setClient(project.getClient());
        response.setDescription(project.getDescription());
        response.setProjectStatus(project.getProjectStatus());

        response.setStartDate(project.getStartDate());
        response.setEndDate(project.getEndDate());

        response.setProjectPriority(project.getProjectPriority());
       // response.setTeamName(project.getTeam().getTeamName());



        return response;
    }





    @Override
    public ProjectDTO updateProject(String id, ProjectDTO projectDTO) {

        Project projectByID=projectRepository.findById(id)
                .orElseThrow(()->new ProjectNotFoundException("Project not found with id "+id));

        projectByID.setProjectId(projectDTO.getProjectId());
        projectByID.setTitle(projectDTO.getTitle());
        projectByID.setClient(projectDTO.getClient());

        projectByID.setDescription(projectDTO.getDescription());

        projectByID.setProjectPriority(projectDTO.getProjectPriority());
        projectByID.setProjectStatus(projectDTO.getProjectStatus());
        projectByID.setStartDate(projectDTO.getStartDate());
        projectByID.setEndDate(projectDTO.getEndDate());

        Project savedProject = projectRepository.save(projectByID);


//        List<Team> teams = new ArrayList<>();
//        for (String teamName : projectDTO.getTeamName()) {
//
//            Team team = teamRepository.findByTeamName(teamName)
//                    .orElseThrow(() -> new TeamNotFoundException("Team not found with name: " + projectDTO.getTeamName()));
//            teams.add(team);
//        }
//        projectByID.setTeams(teams);
//
//
//        List<Department> departments = new ArrayList<>();
//        for(String department: projectDTO.getDepartment()) {
//            Department departmentDetails = departmentRepository.findByDepartmentName(department)
//                    .orElseThrow(()-> new DepartmentNotFoundException("Department not found with name: " + projectDTO.getDepartment()));
//            departments.add(departmentDetails);
//
//        }
//        projectByID.setDepartments(departments);

        projectRepository.save(savedProject);
        return projectDTO;
    }


    @Override
    public ProjectDTO deleteProject(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id "+id));

        for (Employee emp : project.getEmployees()) {
            emp.getProjects().remove(project);
            employeeRepository.save(emp);
        }
        project.getEmployees().clear();

        projectRepository.delete(project);
        return modelMapper.map(project, ProjectDTO.class);
    }

}
