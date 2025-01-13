package com.project.backend.myCarcaddy.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.backend.myCarcaddy.exception.EmployeeNotFoundException;
import com.project.backend.myCarcaddy.model.Employee;
import com.project.backend.myCarcaddy.repository.EmployeeRepository;
import com.project.backend.myCarcaddy.service.EmployeeService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080") // Allow requests from frontend
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private EmployeeRepository employeeRepository;
   

//    @PostMapping("/registerEmployee") 
//    public ResponseEntity<Map<String, Object>> addEmployee(@RequestBody Employee employee) {
//        Employee savedEmployee = employeeService.addEmployee(employee);
//
//        // Create a filtered response
//        Map<String, Object> response = new HashMap<>();
//        response.put("employeeId", savedEmployee.getEmployeeId());
//        response.put("defaultPassword", savedEmployee.getDefaultPassword());
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
//    }
    
    @PostMapping("/registerEmployee")
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            // Collect validation error messages
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });

            // Return a bad request with validation error messages
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // This is fine as ResponseEntity<Object>
        }

        Employee savedEmployee = employeeService.addEmployee(employee);

        // Create a filtered response
        Map<String, Object> response = new HashMap<>();
        response.put("employeeId", savedEmployee.getEmployeeId());
        response.put("defaultPassword", savedEmployee.getDefaultPassword());

        return new ResponseEntity<>(response, HttpStatus.CREATED); // Also works with ResponseEntity<Object>
    }




    
    @PutMapping("/firstLogin/{empId}")
    public ResponseEntity<Boolean> handleFirstLogin(@PathVariable int empId, @RequestBody Map<String, String> passwordData) {
        // Extract defaultPassword and newPassword from the request body
        System.out.println("HIIIIII");
        System.out.println(empId);
        System.out.println(passwordData.get("defaultPassword"));
        System.out.println(passwordData.get("newPassword"));
        
        String defaultPassword = passwordData.get("defaultPassword");
        String newPassword = passwordData.get("newPassword");

        // Validate newPassword strength
        if (!isStrongPassword(newPassword)) {
            // Return false if password is not strong
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST); // 200 OK with false
        }

        boolean isUpdated = employeeService.replacePasswordIfValid(empId, defaultPassword, newPassword);

        if (!isUpdated) {
            // Return false if password update failed due to invalid default password or employee not found
            return new ResponseEntity<>(false, HttpStatus.OK); // 200 OK with false
        }

        // Return true if password was updated successfully
        return new ResponseEntity<>(true, HttpStatus.OK); // 200 OK with true
    }

    // Utility method to check if the password is strong
    private boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            System.out.println("Password is not strong: Too short");
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

        System.out.println("Password is not strong: Missing required character types");
        return hasUppercase && hasLowercase && hasNumber && hasSpecialChar;
    }


    
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();  // Fetch all employees from the service
        
        
        if (employees.isEmpty()) {
        	throw new EmployeeNotFoundException("Employee's are not found."); // 204 No Content if the list is empty
        }
        return new ResponseEntity<>(employees, HttpStatus.OK); // 200 OK with the list of employees
    } 
    
    @PutMapping("/deactivateEmployee/{employeeId}")
    public ResponseEntity<String> deactivateEmployee(@PathVariable int employeeId) {
        try {
            boolean isDeactivated = employeeService.deactivateEmployee(employeeId);
            if (isDeactivated) {
                return new ResponseEntity<>("Employee deactivated successfully.", HttpStatus.OK);
            } else {
                throw new EmployeeNotFoundException("Employee Id "+employeeId+" not found or already deactivated.");
            }
        } catch (EmployeeNotFoundException e) {
            throw e; // Will be handled by the exception handler
        }
    }

    

    
    @PutMapping("/deactivateExpiredEmployees")
    public ResponseEntity<String> deactivateExpiredEmployees() {
        int deactivatedCount = employeeService.deactivateExpiredEmployees();
        if (deactivatedCount > 0) { 
            return new ResponseEntity<>(deactivatedCount + " employees were deactivated.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No employees needed deactivation.", HttpStatus.OK);
        }
    }
    
    
    @GetMapping("/fetchDeactivatedAccounts")
    public ResponseEntity<List<Employee>> fetchDeactivaedAccounts()
    {
    		Optional<List<Employee>> employessOptional = employeeRepository.findAllByStatus("inactive");
    		if(employessOptional.isPresent())
    		{
    			List<Employee>employees= employessOptional.get();
    			return new ResponseEntity<>(employees,HttpStatus.OK);
    		}
    		else {
				throw new EmployeeNotFoundException("Employee's are not found with inactive status");
			}
    }

    
}
