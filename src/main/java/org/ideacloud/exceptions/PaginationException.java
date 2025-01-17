package org.ideacloud.exceptions;

public class PaginationException extends RuntimeException {
    public PaginationException() {
        super(ErrorCode.PAGINATION_ERROR.getMessage());
    }
}
