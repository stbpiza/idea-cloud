package org.ideacloud.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ideacloud.application.GetKeywordListService;
import org.ideacloud.dtos.KeywordListDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "04.Keyword", description = "키워드 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/keywords")
public class KeywordController {

    private final GetKeywordListService getKeywordListService;

    @Operation(summary = "keyword list 조회", description = "keyword list를 조회합니다. ")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public KeywordListDto listKeywords(@RequestParam Integer page,
                                       @RequestParam Integer size) {
        return getKeywordListService.getKeywordList(page, size);
    }
}
