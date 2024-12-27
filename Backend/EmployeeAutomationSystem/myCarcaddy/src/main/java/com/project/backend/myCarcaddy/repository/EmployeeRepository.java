package com.project.backend.myCarcaddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.project.backend.myCarcaddy.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	
		Optional<Employee> findByEmployeeEmail(String email);
}
