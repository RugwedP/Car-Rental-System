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

    // @PutMapping("/firstLogin/{empId}")
    // public ResponseEntity<String> handleFirstLogin(@PathVariable int empId,
    // @RequestBody Map<String, String> passwordData) {
    // // Extract defaultPassword and newPassword from the request body
    // String defaultPassword = passwordData.get("defaultPassword");
    // String newPassword = passwordData.get("newPassword");
    //
    // // Validate newPassword strength
    // if (!isStrongPassword(newPassword)) {
    // return new ResponseEntity<>(
    // "New password must be at least 8 characters long, and include an uppercase
    // letter, a lowercase letter, a number, and a special character.",
    // HttpStatus.BAD_REQUEST
    // ); // 400 Bad Request
    // }

    // boolean isUpdated = employeeService.replacePasswordIfValid(empId,
    // defaultPassword, newPassword);

    // if (!isUpdated) {
    // return new ResponseEntity<>("Invalid default password or employee not
    // found.", HttpStatus.UNAUTHORIZED); // 401 Unauthorized
    // }

    // return new ResponseEntity<>("Password updated successfully.", HttpStatus.OK);
    // // 200 OK
    // }

    @PutMapping("/firstLogin/{email}")
    public ResponseEntity<String> handleFirstLogin(@PathVariable String email, @RequestBody Map<String, String> passwordData) {
        // Extract defaultPassword and newPassword from the request body
    	System.out.println("HI");
    	System.out.println(email);
    	System.out.println(passwordData.get("defaultPassword"));
    	System.out.println(passwordData.get("newPassword"));
        String defaultPassword = passwordData.get("defaultPassword");
        String newPassword = passwordData.get("newPassword");

        // Validate newPassword strength
        if (!isStrongPassword(newPassword)) {
            return new ResponseEntity<>(
                "New password must be at least 8 characters long, and include an uppercase letter, a lowercase letter, a number, and a special character.", 
                HttpStatus.BAD_REQUEST
            ); // 400 Bad Request
        }

        boolean isUpdated = employeeService.replacePasswordIfValid(email, defaultPassword, newPassword);
        if (!isUpdated) {
            return new ResponseEntity<>("Invalid default password or employee not found.", HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }
        return new ResponseEntity<>("Password updated successfully.", HttpStatus.OK); // 200 OK
    }

    // Utility method to check if the password is strong
    private boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }

            // Break early if all conditions are met
            if (hasUppercase && hasLowercase && hasNumber && hasSpecialChar) {
                return true;
            }
        }

        return hasUppercase && hasLowercase && hasNumber && hasSpecialChar;
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees(); // Fetch all employees from the service
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if the list is empty
        }
        return new ResponseEntity<>(employees, HttpStatus.OK); // 200 OK with the list of employees
    }
    @PutMapping("/deactivateEmployee/{employeeId}")
    public ResponseEntity<String> deactivateEmployee(@PathVariable int employeeId) {
        boolean isDeactivated = employeeService.deactivateEmployee(employeeId);
        if (isDeactivated) {
            return new ResponseEntity<>("Employee deactivated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found or already deactivated.", HttpStatus.NOT_FOUND);
        }
    }
    
    

}
