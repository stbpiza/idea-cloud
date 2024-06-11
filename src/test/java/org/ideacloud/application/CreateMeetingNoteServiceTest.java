package org.ideacloud.application;

import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.models.Keyword;
import org.ideacloud.models.KeywordHistory;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.models.User;
import org.ideacloud.repositories.KeywordHistoryRepository;
import org.ideacloud.repositories.KeywordRepository;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CreateMeetingNoteServiceTest {

    private MeetingNoteRepository meetingNoteRepository;
    private KeywordRepository keywordRepository;
    private KeywordHistoryRepository keywordHistoryRepository;

    private String title;
    private String body;
    private Long userId;
    private List<MeetingNoteCreateDto.AddKeywordToMeetingNoteDto> keywords;
    private Keyword keyword1;
    private Keyword keyword2;
    private MeetingNote meetingNote;

    @BeforeEach
    void setUpMockObjects() {
        meetingNoteRepository = mock(MeetingNoteRepository.class);
        keywordRepository = mock(KeywordRepository.class);
        keywordHistoryRepository = mock(KeywordHistoryRepository.class);
    }

    @BeforeEach
    void setUpFixtures() {
        title = "title";
        body = """
               body
               keyword
               keyword
               body
               body
               
               """;
        userId = 1L;
        meetingNote = MeetingNote.builder()
                .title(title)
                .body(body)
                .user(new User(userId))
                .build();
        keywords = List.of(new MeetingNoteCreateDto.AddKeywordToMeetingNoteDto("keyword", 2),
                            new MeetingNoteCreateDto.AddKeywordToMeetingNoteDto("body", 3));
        keyword1 = new Keyword("keyword");
        keyword2 = new Keyword("body");
    }

    @Test
    void createMeetingNote_TEST() {
        CreateMeetingNoteService createMeetingNoteService = new CreateMeetingNoteService(meetingNoteRepository, keywordRepository, keywordHistoryRepository);

        given(keywordRepository.findAllByKeywordIn(List.of("keyword", "body"))).willReturn(List.of());

        createMeetingNoteService.createMeetingNote(title, body, userId, keywords);

        verify(meetingNoteRepository).save(any(MeetingNote.class));
        verify(keywordRepository, times(1)).saveAll(any());
        verify(keywordHistoryRepository, times(1)).saveAll(any());
    }

    @Test
    void addKeywords_TEST() {
        CreateMeetingNoteService createMeetingNoteService = new CreateMeetingNoteService(meetingNoteRepository, keywordRepository, keywordHistoryRepository);

        given(keywordRepository.findAllByKeywordIn(List.of("keyword", "body"))).willReturn(List.of());

        Map<Keyword, Integer> newKeywords = createMeetingNoteService.addKeywords(keywords);

        assertThat(newKeywords).hasSize(2);
        for (Keyword keyword : newKeywords.keySet()) {
            assertThat(keyword.keyword()).isIn("keyword", "body");
        }
    }

    @Test
    void getNewKeywords_TEST() {
        CreateMeetingNoteService createMeetingNoteService = new CreateMeetingNoteService(meetingNoteRepository, keywordRepository, keywordHistoryRepository);

        given(keywordRepository.findAllByKeywordIn(List.of("keyword", "body"))).willReturn(List.of(keyword1));

        List<Keyword> newKeywords = createMeetingNoteService.getNewKeywords(List.of("keyword", "body"), List.of(keyword1));

        assertThat(newKeywords).hasSize(1);
        assertThat(newKeywords.get(0).keyword()).isEqualTo("body");
    }

    @Test
    void getKeywordCountMap_TEST() {
        CreateMeetingNoteService createMeetingNoteService = new CreateMeetingNoteService(meetingNoteRepository, keywordRepository, keywordHistoryRepository);

        given(keywordRepository.findAllByKeywordIn(List.of("keyword", "body"))).willReturn(List.of(keyword1, keyword2));

        Map<Keyword, Integer> keywordCountMap = createMeetingNoteService.getKeywordCountMap(List.of(keyword1, keyword2), keywords);

        assertThat(keywordCountMap).hasSize(2);
        for (Keyword keyword : keywordCountMap.keySet()) {
            assertThat(keyword.keyword()).isIn("keyword", "body");
        }
    }

    @Test
    void addKeywordHistories_TEST() {
        CreateMeetingNoteService createMeetingNoteService = new CreateMeetingNoteService(meetingNoteRepository, keywordRepository, keywordHistoryRepository);

        Map<Keyword, Integer> keywordMap = Map.of(keyword1, 2, keyword2, 3);

        createMeetingNoteService.addKeywordHistories(keywordMap, meetingNote);

        ArgumentCaptor<List<KeywordHistory>> keywordHistoryCaptor = ArgumentCaptor.forClass(List.class);
        verify(keywordHistoryRepository).saveAll(keywordHistoryCaptor.capture());

        List<KeywordHistory> keywordHistories = keywordHistoryCaptor.getValue();
        assertThat(keywordHistories).hasSize(2);
        for (KeywordHistory keywordHistory : keywordHistories) {
            assertThat(keywordHistory.getKeyword()).isIn(keyword1, keyword2);
            assertThat(keywordHistory.getCount()).isIn(2, 3);
        }
    }
}