		package com.project.carrentalsystem.Model;
		import jakarta.persistence.Entity;
		import jakarta.persistence.GeneratedValue;
		import jakarta.persistence.GenerationType;
		import jakarta.persistence.Id;
		import java.time.LocalDate;
		
		@Entity
		public class Employee {
		
			
			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private int employeeId;
		
		    private String employeeName;
		    private LocalDate dateOfBirth;
		    private String employeeEmail;
		    private String accountType; // temporary or permanent
		    private String defaultPassword;
		    private LocalDate accountExpiryDate;
		    private String status; // active or inactive
		    private String designation;
		    
		
		    // Getters and Setters
		    
		    
		    
		    public int getEmployeeId() {
		        return employeeId;
		    }
		
		    public String getDesignation() {
				return designation;
			}
	
			public void setDesignation(String designation) {
				this.designation = designation;
			}
	
		
			public void setEmployeeId(int employeeId) {
		        this.employeeId = employeeId;
		    }
		
		    public String getEmployeeName() {
		        return employeeName;
		    }
		
		    public void setEmployeeName(String employeeName) {
		        this.employeeName = employeeName;
		    }
		
		    public LocalDate getDateOfBirth() {
		        return dateOfBirth;
		    }
		
		    public void setDateOfBirth(LocalDate dateOfBirth) {
		        this.dateOfBirth = dateOfBirth;
		    }
		
		    public String getEmployeeEmail() {
		        return employeeEmail;
		    }
		
		    public void setEmployeeEmail(String employeeEmail) {
		        this.employeeEmail = employeeEmail;
		    }
		
		    public String getAccountType() {
		        return accountType;
		    }
		
		    public void setAccountType(String accountType) {
		    	System.out.println("?????????"+accountType);
		        this.accountType = accountType;
		    }
		
		    public String getDefaultPassword() {
		        return defaultPassword;
		    }
		
		    public void setDefaultPassword(String defaultPassword) {
		        this.defaultPassword = defaultPassword;
		    }
		
		    public LocalDate getAccountExpiryDate() {
		        return accountExpiryDate;
		    }
		
		    public void setAccountExpiryDate(LocalDate accountExpiryDate) {
		        this.accountExpiryDate = accountExpiryDate;
		    }
		
		    public String getStatus() {
		        return status;
		    }
		
		    public void setStatus(String status) {
		        this.status = status;
		    }
		}
