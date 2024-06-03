package org.ideacloud.application;

import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.models.Keyword;
import org.ideacloud.repositories.KeywordHistoryRepository;
import org.ideacloud.repositories.KeywordRepository;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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
        keywords = List.of(new MeetingNoteCreateDto.AddKeywordToMeetingNoteDto("keyword", 2),
                            new MeetingNoteCreateDto.AddKeywordToMeetingNoteDto("body", 3));
        keyword1 = new Keyword("keyword");
        keyword2 = new Keyword("body");
    }

    @Test
    void createMeetingNote() {
        CreateMeetingNoteService createMeetingNoteService = new CreateMeetingNoteService(meetingNoteRepository, keywordRepository, keywordHistoryRepository);



        createMeetingNoteService.createMeetingNote(title, body, userId, new ArrayList<>());

        // assertThat(order.status()).isEqualTo(newStatus);
    }
}