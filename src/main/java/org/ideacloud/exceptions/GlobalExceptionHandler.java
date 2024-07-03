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
}
