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
    public String handleEmailAlreadyTaken(EmailAlreadyTaken e) {
        log.error("Email already taken: {}", e.getMessage());
        return "Email already taken";
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(BadRequestException e) {
        log.error("Bad request: {}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(UnauthorizedException e) {
        log.error("Unauthorized: {}", e.getMessage());
        return e.getMessage();
    }
}
