package org.ideacloud.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Method argument not valid: {}", e.getMessage());
        return new ErrorDto(
                ErrorCode.BAD_REQUEST,
                e.getMessage()
        );
    }

    @ExceptionHandler(PaginationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handlePaginationException(PaginationException e) {
        log.error("Pagination error: {}", e.getMessage());
        return new ErrorDto(
                ErrorCode.PAGINATION_ERROR,
                e.getMessage()
        );
    }

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleTeamNotFoundException(TeamNotFoundException e) {
        log.error("Team not found: {}", e.getMessage());
        return new ErrorDto(
                ErrorCode.TEAM_NOT_FOUND,
                e.getMessage()
        );
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(Exception e) {
        log.error("Internal server error: {}", e.getMessage());
        log.error(getErrorLine(e.getStackTrace()));
        return new ErrorDto(
                ErrorCode.DATABASE_ERROR,
                e.getMessage()
        );
    }


    // TODO: 나중에 필요하면 주석 해제
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorDto handleConstraintViolationException(ConstraintViolationException e) {
//        log.error("Constraint violation: {}", e.getMessage());
//        return new ErrorDto(
//                ErrorCode.BAD_REQUEST,
//                e.getMessage()
//        );
//    }
//
//    @ExceptionHandler(ConversionFailedException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorDto handleConversionFailedException(ConversionFailedException e) {
//        log.error("Conversion failed: {}", e.getMessage());
//        return new ErrorDto(
//                ErrorCode.BAD_REQUEST,
//                e.getMessage()
//        );
//    }
//
//    @ExceptionHandler(InvalidFormatException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorDto handleInvalidFormatException(InvalidFormatException e) {
//        log.error("Invalid format: {}", e.getMessage());
//        return new ErrorDto(
//                ErrorCode.BAD_REQUEST,
//                e.getMessage()
//        );
//    }


    protected String getErrorLine(StackTraceElement[] stackTrace) {
        StringBuilder errorLog = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (stackTraceElement.getClassName().contains("org.ideacloud")) {
                errorLog.append(stackTraceElement).append("\n");
            }
        }
        return errorLog.toString();
    }
}
