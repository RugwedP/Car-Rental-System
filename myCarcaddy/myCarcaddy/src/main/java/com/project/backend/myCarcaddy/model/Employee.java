package com.project.backend.myCarcaddy.model;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;

	@NotNull
	@NotEmpty(message = "Provide value for employee name")
    private String employeeName;
    
	@NotNull(message = "Date of birth should not be empty")
	@PastOrPresent(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    
	@NotNull(message = "Email cannot be null")
	@NotEmpty(message = "Email cannot be Empty")
	@Email(message = "Email should be valid")
    private String employeeEmail;
    
    private String accountType; // temporary or permanent
    
    private String defaultPassword;
    
    
    @NotNull
	@NotEmpty(message = "Provide value for designation")
    private String designation;
    
    @FutureOrPresent(message = "AccountExpiryDate date should be either current or future date")
    private LocalDate accountExpiryDate;
    
    @NotNull
	@NotEmpty(message = "Provide value for status")
    private String status; // active or inactive

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    @JsonIgnoreProperties("employee")
    private List<RentBooking> rentBookings;
   
    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public List<RentBooking> getRentBookings() {
		return rentBookings;
	}

	public void setRentBookings(List<RentBooking> rentBookings) {
		this.rentBookings = rentBookings;
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
    
    
}