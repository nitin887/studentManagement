package com.studentManagementSystem.studentManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EnrollmentAlreadyExistsException extends RuntimeException{
    public EnrollmentAlreadyExistsException(String message) {
        super(message);
    }
}
