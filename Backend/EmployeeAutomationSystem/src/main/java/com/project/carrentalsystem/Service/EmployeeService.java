package com.project.carrentalsystem.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.carrentalsystem.Model.Employee;
import com.project.carrentalsystem.repository.EmployeeRepo;

@Service
public class EmployeeService {

	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	public Employee addEmployee(Employee employee) {
        // Set default status if not provided
		System.out.println("**************"+employee.getAccountType());
        if (employee.getStatus() == null || employee.getStatus().isEmpty()) {
            employee.setStatus("active");
        }

     // Generate default password based on account type, DOB, and name length
        if (employee.getDefaultPassword() == null || employee.getDefaultPassword().isEmpty()) {
            String accountTypePrefix = "P"; // Default to Permanent
            if ("temporary".equalsIgnoreCase(employee.getAccountType())) {
                accountTypePrefix = "T";
            }

            // Extract DOB and convert to YYYYMMDD format
            LocalDate dob = employee.getDateOfBirth();
            String dobFormatted = dob != null ? dob.toString().replaceAll("-", "") : "00000000";

            // Calculate the length of the employee name
            int nameLength = employee.getEmployeeName() != null ? employee.getEmployeeName().length() : 0;

            // Construct the password
            String defaultPassword = accountTypePrefix + dobFormatted + nameLength;
            employee.setDefaultPassword(defaultPassword);
        }


        // Set account expiry date automatically for 'temporary' account type
        if ("temporary".equalsIgnoreCase(employee.getAccountType())) {
            LocalDate expiryDate = LocalDate.now().plusYears(1); // 1 year from now
            employee.setAccountExpiryDate(expiryDate);
        }
        System.out.println("Default Password: " + employee.getDefaultPassword());


        // Save the employee to the database
        return employeeRepo.save(employee); 
    }
}
