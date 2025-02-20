package com.project.backend.myCarcaddy.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.backend.myCarcaddy.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	
		Optional<Employee> findByEmployeeEmail(String email);
		
		
		
		@Modifying
	    @Transactional
	    
		 @Query("UPDATE Employee e SET e.status = 'inactive' WHERE e.accountExpiryDate < :currentDate AND e.status = 'active'")
		    int deactivateExpiredEmployees(@Param("currentDate") LocalDate currentDate);
		
		
		Optional<List<Employee>> findAllByStatus(String status);
		
	    List<Employee> findByEmployeeNameContainingIgnoreCase(String name);

}
