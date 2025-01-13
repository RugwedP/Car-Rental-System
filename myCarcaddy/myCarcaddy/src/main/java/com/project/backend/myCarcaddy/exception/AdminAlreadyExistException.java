package com.project.backend.myCarcaddy.exception;

public class AdminAlreadyExistException extends RuntimeException {
	public AdminAlreadyExistException(String message) {
        super(message);
    }
}
