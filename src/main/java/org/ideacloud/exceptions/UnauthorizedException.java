package org.ideacloud.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED.getMessage());
    }
    public UnauthorizedException(Exception ex) {
        super(ex);
    }
}
