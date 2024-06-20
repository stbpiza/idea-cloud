package org.ideacloud.controllers;

import lombok.RequiredArgsConstructor;
import org.ideacloud.application.GetKeywordListService;
import org.ideacloud.dtos.KeywordListDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/keywords")
public class KeywordController {

    private final GetKeywordListService getKeywordListService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public KeywordListDto listKeywords(@RequestParam Integer page,
                                       @RequestParam Integer size) {
        return getKeywordListService.getKeywordList(page, size);
    }
}
