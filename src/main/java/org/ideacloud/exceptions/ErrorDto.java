package org.ideacloud.exceptions;

public record ErrorDto(
        ErrorCode errorCode,
        String message
) {
}
