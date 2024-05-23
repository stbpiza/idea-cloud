package org.ideacloud.dtos;

public record LoginRequestDto(
        String email,
        String password
) {
}
