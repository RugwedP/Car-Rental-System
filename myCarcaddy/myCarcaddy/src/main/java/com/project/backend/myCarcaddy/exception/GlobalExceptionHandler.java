package com.project.backend.myCarcaddy.exception;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
		Map<String, String> errorResponse = new HashMap<>();
	    errorResponse.put("error", ex.getMessage()); // Assume the error is related to the 'email' field
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

//    @ExceptionHandler(AdminNotFoundException.class)
//    public ResponseEntity<String> handleAdminNotFoundException(AdminNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
	
	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleAdminNotFoundException(AdminNotFoundException ex) {
	    Map<String, String> errorResponse = new HashMap<>();
	    errorResponse.put("error", ex.getMessage()); // Assume the error is related to the 'email' field
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	
	

    @ExceptionHandler(AdminAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleAdminAlreadyExistsException(AdminAlreadyExistException ex) {
    	Map<String, String> errorResponse = new HashMap<>();
	    errorResponse.put("error", ex.getMessage()); // Assume the error is related to the 'email' field
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

//    @ExceptionHandler(EmployeeAlreadyExistsException.class)
//    public ResponseEntity<String> handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(PasswordNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePasswordNotFoundException(PasswordNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException1(Exception ex) {
    	 Map<String, String> errorResponse = new HashMap<>();
         errorResponse.put("error", ex.getMessage());
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // Loop through the validation errors and add them to the errors map
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField(); // Get field name
            String errorMessage = error.getDefaultMessage();   // Get error message
            errors.put(fieldName, errorMessage);               // Store error message for that field
        });
        
        // Return a response with validation errors and a 400 Bad Request status
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
 


}
