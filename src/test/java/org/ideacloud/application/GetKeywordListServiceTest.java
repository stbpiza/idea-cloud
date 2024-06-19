package org.ideacloud.application;

import org.ideacloud.models.Keyword;
import org.ideacloud.repositories.KeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetKeywordListServiceTest {

    private KeywordRepository keywordRepository;
    private GetKeywordListService getKeywordListService;

    private Keyword keyword1;
    private Keyword keyword2;

    @BeforeEach
    void setUpMockObjects() {
        keywordRepository = mock(KeywordRepository.class);
        getKeywordListService = new GetKeywordListService(keywordRepository);
    }

    @BeforeEach
    void setUpFixtures() {
        keyword1 = new Keyword("keyword1");
        keyword2 = new Keyword("keyword2");
    }


    @Test
    void getKeywordList_TEST() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Keyword> keywords = new PageImpl<>(List.of(keyword1, keyword2),
                pageable, 2);

        given(keywordRepository.findAllByOrderByIdDesc(pageable))
                .willReturn(keywords);

        var keywordListDto = getKeywordListService.getKeywordList(0, 10);

        assertEquals(2, keywordListDto.keywords().size());
        assertEquals(2, keywordListDto.totalElements());
        assertEquals(1, keywordListDto.totalPages());
    }
}