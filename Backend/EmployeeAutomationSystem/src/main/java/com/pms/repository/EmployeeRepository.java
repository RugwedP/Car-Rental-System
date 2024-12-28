package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pms.model.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.time.LocalDate; 
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmployeeEmail(String email);
    @Modifying
	    @Transactional
	    
		 @Query("UPDATE Employee e SET e.status = 'inactive' WHERE e.accountExpiryDate < :currentDate AND e.status = 'active'")
		    int deactivateExpiredEmployees(@Param("currentDate") LocalDate currentDate);
}
