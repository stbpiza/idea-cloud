package org.ideacloud.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ideacloud.application.CheckEmailService;
import org.ideacloud.application.SignupService;
import org.ideacloud.dtos.EmailCheckDto;
import org.ideacloud.dtos.SignupRequestDto;
import org.ideacloud.dtos.SignupResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final SignupService signupService;
    private final CheckEmailService checkEmailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResultDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        String accessToken = signupService.signup(
                signupRequestDto.email().trim(),
                signupRequestDto.name().trim(),
                signupRequestDto.password().trim()
        );

        return new SignupResultDto(accessToken);
    }

    @PostMapping("/check-email")
    @ResponseStatus(HttpStatus.OK)
    public String checkEmail(@Valid @RequestBody EmailCheckDto emailCheckDto) {
        checkEmailService.checkEmail(emailCheckDto.email().trim());

        return "OK";
    }
}
