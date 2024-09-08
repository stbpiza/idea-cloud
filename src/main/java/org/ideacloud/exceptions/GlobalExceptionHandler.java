package org.ideacloud.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyTaken.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleEmailAlreadyTaken(EmailAlreadyTaken e) {
        log.error("Email already taken: {}", e.getMessage());
        return new ErrorDto(
                ErrorCode.EMAIL_ALREADY_USED,
                ErrorCode.EMAIL_ALREADY_USED.getMessage()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequest(BadRequestException e) {
        log.error("Bad request: {}", e.getMessage());
        return new ErrorDto(
                ErrorCode.BAD_REQUEST,
                e.getMessage()
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleUnauthorized(UnauthorizedException e) {
        log.error("Unauthorized: {}", e.getMessage());
        return new ErrorDto(
                ErrorCode.UNAUTHORIZED,
                e.getMessage()
        );
    }
}
