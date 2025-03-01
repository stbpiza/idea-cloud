package org.ideacloud.exceptions;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(String message) {
        super(message);
    }
}
