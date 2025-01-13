package com.project.backend.myCarcaddy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.backend.myCarcaddy.exception.AdminAlreadyExistException;
import com.project.backend.myCarcaddy.exception.AdminNotFoundException;
import com.project.backend.myCarcaddy.exception.EmployeeNotFoundException;
import com.project.backend.myCarcaddy.exception.PasswordNotFoundException;
import com.project.backend.myCarcaddy.model.Admin;
import com.project.backend.myCarcaddy.model.Employee;
import com.project.backend.myCarcaddy.repository.AdminRepository;
import com.project.backend.myCarcaddy.repository.EmployeeRepository;


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    // Admin login logic
    public Map<String, String> login(String email, String password) {
        Map<String, String> response = new HashMap<>();

        Optional<Admin> existingAdmin = adminRepository.findByEmail(email);
        if (existingAdmin.isPresent()) {
            Admin foundAdmin = existingAdmin.get();
            if (foundAdmin.getPassword().equals(password)) {
                response.put("message", "Login successful!");
            } else {
                throw new PasswordNotFoundException("Incorrect password.");
            }
        } else {
            throw new AdminNotFoundException("Admin not found.");
        }

        return response;
    }


    // Admin registration logic
    public Map<String, String> register(Admin admin) {
        Map<String, String> response = new HashMap<>();

        // Check if the username already exists
        if (adminRepository.findByUsername(admin.getUsername()).isPresent()) {
            throw new AdminAlreadyExistException("Username already exists.");
        }

        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new AdminAlreadyExistException("Email already exists.");
        }

        adminRepository.save(admin);
        response.put("message", "Admin registered successfully!");
        
        return response;
    }
    
    // find employee by id
    public Employee findEmployeeById(String empId) {
        if (empId == null || empId.isEmpty()) {
            throw new IllegalArgumentException("Employee ID must not be empty.");
        }

        try {
            int id = Integer.parseInt(empId);
            return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid employee ID format: " + empId);
        }
    }
    
    
    // find employee by name or id
    public Object searchEmployee(String empId, String name) {
        if ((empId == null || empId.isEmpty()) && (name == null || name.isEmpty())) {
            throw new IllegalArgumentException("Either employee ID or name must be provided.");
        }

        if (empId != null && !empId.isEmpty()) {
            try {
                int id = Integer.parseInt(empId); // Parse the employee ID
                // Search by employee ID
                return employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + empId));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid employee ID format: " + empId);
            }
        } else if (name != null && !name.isEmpty()) {
            // Search by employee name
            List<Employee> employees = employeeRepository.findByEmployeeNameContainingIgnoreCase(name);
            if (employees.isEmpty()) {
                throw new EmployeeNotFoundException("No employees found with name: " + name);
            }
            return employees; // Return the list of employees matching the name
        }

        // Fallback if no valid input was found
        throw new IllegalArgumentException("Invalid search parameters.");
    }



}
