package com.project.backend.myCarcaddy.exception;

public class PasswordNotFoundException extends RuntimeException {
	public PasswordNotFoundException(String message) {
        super(message);
    }
}
