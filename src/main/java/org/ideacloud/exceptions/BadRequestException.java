package org.ideacloud.exceptions;


public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST.getMessage());
    }
    public BadRequestException(String message) { super(message); }
    public BadRequestException(Exception ex) {
        super(ex);
    }
}
