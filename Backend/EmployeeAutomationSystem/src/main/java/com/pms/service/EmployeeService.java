package com.pms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.model.Employee;
import com.pms.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee addEmployee(Employee employee) {
		// Set default status if not provided
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
		
		// Set account date automatically for 'temporary' account type
		if ("temporary".equalsIgnoreCase(employee.getAccountType())) {
			LocalDate expiryDate = LocalDate.now().plusYears(1); // 1 year from now
			employee.setAccountExpiryDate(expiryDate);
		}
		System.out.println("Default Password: " + employee.getDefaultPassword());

		// Save the employee to the database
		return employeeRepository.save(employee);
	}

	public boolean replacePasswordIfValid(String email, String defaultPassword, String newPassword) {
		// Find the employee by ID
		Optional<Employee> employeeOptional = employeeRepository.findByEmployeeEmail(email);

		if (employeeOptional.isPresent()) {
			Employee employee = employeeOptional.get();

			// Check if the provided default password matches the one in the database
			if (employee.getDefaultPassword().equals(defaultPassword)) {
				// Update the password to the new password
				employee.setDefaultPassword(newPassword);
				employeeRepository.save(employee); // Save the updated employee
				return true;
			}
		}
		// Return false if the employee doesn't exist or the passwords don't match
		return false;
	}

	public List<Employee> getAllEmployees() {
    // Fetch all employees from the database
    List<Employee> employees = employeeRepository.findAll();

    // Filter out employees whose status is not "Active"
    return employees.stream()
                    .filter(employee -> "Active".equalsIgnoreCase(employee.getStatus()))
                    .collect(Collectors.toList());
}



public boolean deactivateEmployee(int employeeId) {
	Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
	if (employeeOptional.isPresent()) {
		Employee employee = employeeOptional.get();

		// Check if the employee is active before deactivating
		if ("Active".equalsIgnoreCase(employee.getStatus())) {
			employee.setStatus("inactive");
			employeeRepository.save(employee);
			return true;
		}
	}
	return false;
}
	

}



