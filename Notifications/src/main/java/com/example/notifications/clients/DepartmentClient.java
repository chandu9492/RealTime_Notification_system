package com.example.notifications.clients;





import com.example.notifications.dtos.EmployeeDepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-service", url = "http://localhost:8091", contextId = "departmentClient")
public interface DepartmentClient {

    @GetMapping("/api/department/{departmentId}/employees")
    EmployeeDepartmentDTO getEmployeesInDepartment(@PathVariable("departmentId") String departmentId);
}
