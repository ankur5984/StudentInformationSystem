package com.app.core.StudentInformationSystem.exceptionHandler;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String _message) {
        super(_message);
    }
}
