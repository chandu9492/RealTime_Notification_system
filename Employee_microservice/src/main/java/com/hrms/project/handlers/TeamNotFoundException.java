package com.hrms.project.handlers;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(String message) {
        super(message);
    }
}
