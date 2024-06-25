package org.ideacloud.controllers;

import org.ideacloud.application.GetKeywordListService;
import org.ideacloud.dtos.KeywordDto;
import org.ideacloud.dtos.KeywordListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(KeywordController.class)
class KeywordControllerTest extends ControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetKeywordListService getKeywordListService;



    @Test
    @DisplayName("GET /api/keywords")
    void listKeywords() throws Exception {
        // given
        var keywordListDto = new KeywordListDto(List.of(
                new KeywordDto(1L, "keyword1", 1, 1),
                new KeywordDto(2L, "keyword2", 2, 2))
                , 2L, 1);

        given(getKeywordListService.getKeywordList(0, 10))
                .willReturn(keywordListDto);

        // when
        mockMvc.perform(get("/api/keywords")
                        .header("Authorization", "Bearer " + userAccessToken)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("keyword1")))
                .andExpect(content().string(containsString("keyword2")));
    }

}