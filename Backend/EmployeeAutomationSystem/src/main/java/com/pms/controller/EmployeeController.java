package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pms.model.Employee;
import com.pms.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/registerEmployee") 
    public ResponseEntity<Map<String, Object>> addEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.addEmployee(employee);

        // Create a filtered response
        Map<String, Object> response = new HashMap<>();
        response.put("employeeId", savedEmployee.getEmployeeId());
        response.put("defaultPassword", savedEmployee.getDefaultPassword());

        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
    }
    
    @PutMapping("/firstLogin/{empId}")
    public ResponseEntity<String> handleFirstLogin(@PathVariable int empId, @RequestBody Map<String, String> passwordData) {
        // Extract defaultPassword and newPassword from the request body
        String defaultPassword = passwordData.get("defaultPassword");
        String newPassword = passwordData.get("newPassword");

        boolean isUpdated = employeeService.replacePasswordIfValid(empId, defaultPassword, newPassword);

        if (!isUpdated) {
            return new ResponseEntity<>("Invalid default password or employee not found.", HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }

        return new ResponseEntity<>("Password updated successfully.", HttpStatus.OK); // 200 OK
    }
    
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();  // Fetch all employees from the service
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if the list is empty
        }
        return new ResponseEntity<>(employees, HttpStatus.OK); // 200 OK with the list of employees
    } 

}
