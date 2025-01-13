package com.project.backend.myCarcaddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.backend.myCarcaddy.model.Admin;

import java.util.Optional;
/*
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByEmail(String email);
}*/
public interface AdminRepository extends JpaRepository<Admin, Integer> { // Correct generic type
    Optional<Admin> findByUsername(String username);

    Optional<Admin> findByEmail(String email);
}
