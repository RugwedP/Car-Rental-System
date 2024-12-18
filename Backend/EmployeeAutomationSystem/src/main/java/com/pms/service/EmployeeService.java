package com.pms.service;


import java.time.LocalDate;

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

	        // Set default password if not provided
	        if (employee.getDefaultPassword() == null || employee.getDefaultPassword().isEmpty()) {
	            employee.setDefaultPassword("password@123");
	        }

	        // Set account expiry date automatically for 'temporary' account type
	        if ("temporary".equalsIgnoreCase(employee.getAccountType())) {
	            LocalDate expiryDate = LocalDate.now().plusYears(1); // 1 year from now
	            employee.setAccountExpiryDate(expiryDate);
	        }

	        // Save the employee to the database
	        return employeeRepository.save(employee); 
	    }

	
}
