package com.project.carrentalsystem.model;

import java.time.LocalDate;

public class Employee {
    private String employeeName;
    private LocalDate dateOfBirth;
    private String employeeEmail;
    private String accountType;
    private String designation;
    private LocalDate accountExpiryDate;
    private String status;

    // Getters and Setters
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    @Override
    public String toString() {
        return "EmployeeForm{" +
                "employeeName='" + employeeName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", accountType='" + accountType + '\'' +
                ", designation='" + designation + '\'' +
                ", accountExpiryDate=" + accountExpiryDate +
                ", status='" + status + '\'' +
                '}';
    }
}
