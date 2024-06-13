package org.ideacloud.application;

import org.ideacloud.dtos.MeetingNoteDetailDto;
import org.ideacloud.models.Keyword;
import org.ideacloud.models.KeywordHistory;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.models.Role;
import org.ideacloud.models.User;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetMeetingNoteDetailServiceTest {

    private MeetingNoteRepository meetingNoteRepository;

    private GetMeetingNoteDetailService getMeetingNoteDetailService;

    @BeforeEach
    void setUp() {
        meetingNoteRepository = mock(MeetingNoteRepository.class);
        getMeetingNoteDetailService = new GetMeetingNoteDetailService(meetingNoteRepository);
    }

    @Test
    void getMeetingNoteDetail() {
        User user = new User("email", "name", "password", Role.ROLE_USER);
        Keyword keyword = new Keyword("keyword");

        MeetingNote meetingNote = mock(MeetingNote.class);
        KeywordHistory keywordHistory = mock(KeywordHistory.class);
        List<KeywordHistory> keywordHistories = List.of(keywordHistory);


        given(meetingNoteRepository.findById(1L))
                .willReturn(Optional.of(meetingNote));
        given(meetingNote.keywordHistories())
                .willReturn(keywordHistories);
        given(meetingNote.user())
                .willReturn(user);
        given(keywordHistory.keyword())
                .willReturn(keyword);

        MeetingNoteDetailDto meetingNoteDetail = getMeetingNoteDetailService.getMeetingNoteDetail(1L);

//        assertThat(meetingNoteDetail.title()).isEqualTo("title");
//        assertThat(meetingNoteDetail.body()).isEqualTo("body");
        assertThat(meetingNoteDetail.userName()).isEqualTo("name");
        assertThat(meetingNoteDetail.keywords()).contains("keyword");

    }
}