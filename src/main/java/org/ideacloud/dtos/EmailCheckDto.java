package org.ideacloud.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailCheckDto(
        @NotBlank
        @Email
        String email
) {
}
