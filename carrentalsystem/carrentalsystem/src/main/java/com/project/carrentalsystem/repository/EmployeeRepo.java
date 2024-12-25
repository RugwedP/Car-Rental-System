package com.project.carrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.carrentalsystem.Model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
	
}
