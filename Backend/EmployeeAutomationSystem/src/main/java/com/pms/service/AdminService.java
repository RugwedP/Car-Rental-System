package com.pms.service;

import com.pms.model.Admin;
import com.pms.repository.AdminRepository;
import com.pms.exceptions.AdminNotFoundException;
import com.pms.exceptions.AdminAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Admin login logic
    public Map<String, String> login(Admin admin) {
        Map<String, String> response = new HashMap<>();

        Optional<Admin> existingAdmin = adminRepository.findByUsername(admin.getUsername());
        if (existingAdmin.isPresent()) {
            Admin foundAdmin = existingAdmin.get();
            if (foundAdmin.getPassword().equals(admin.getPassword())) {
                response.put("message", "Login successful!");
            } else {
                throw new IllegalArgumentException("Incorrect password.");
            }
        } else {
            throw new AdminNotFoundException("Admin not found.");
        }

        return response;
    }

    // Admin registration logic
    public Map<String, String> register(Admin admin) {
        Map<String, String> response = new HashMap<>();

        // Check if the username already exists
        if (adminRepository.findByUsername(admin.getUsername()).isPresent()) {
            throw new AdminAlreadyExistsException("Username already exists.");
        }

        // Check if the email already exists
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new AdminAlreadyExistsException("Email already exists.");
        }

        // Save the new admin in the database
        adminRepository.save(admin);
        response.put("message", "Admin registered successfully!");
        return response;
    }
}
