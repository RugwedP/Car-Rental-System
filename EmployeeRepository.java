package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.pms.model.Employee;
import java.time.LocalDate;  // Import LocalDate
=======
import org.springframework.stereotype.Repository;
import com.pms.model.Employee;
>>>>>>> f10fe6f4fe9d84b00cda93b6e17c2f0c01cac84d

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmployeeEmail(String email);
<<<<<<< HEAD
    
    @Modifying
    @Transactional 
	 @Query("UPDATE Employee e SET e.status = 'inactive' WHERE e.accountExpiryDate < :currentDate AND e.status = 'active'")
	    int deactivateExpiredEmployees(@Param("currentDate") LocalDate currentDate);
}

=======
}
>>>>>>> f10fe6f4fe9d84b00cda93b6e17c2f0c01cac84d
