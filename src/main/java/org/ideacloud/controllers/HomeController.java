package org.ideacloud.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ideacloud.application.GetHomeService;
import org.ideacloud.dtos.HomeResultDto;
import org.ideacloud.security.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "05.Home", description = "홈 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final GetHomeService getHomeService;

        @Operation(summary = "home 조회", description = "home을 조회합니다.")
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public HomeResultDto getHome(Authentication authentication) {
            AuthUser authUser = (AuthUser) authentication.getPrincipal();

            return getHomeService.getHome(authUser.id());
        }
}
