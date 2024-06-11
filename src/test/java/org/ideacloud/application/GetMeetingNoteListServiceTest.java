package org.ideacloud.application;

import org.ideacloud.dtos.MeetingNoteListDto;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.models.User;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetMeetingNoteListServiceTest {

    private MeetingNoteRepository meetingNoteRepository;
    private GetMeetingNoteListService getMeetingNoteListService;


    private User user1;
    private User user2;
    private MeetingNote meetingNote1;
    private MeetingNote meetingNote2;


    @BeforeEach
    void setUpMockObjects() {
        meetingNoteRepository = mock(MeetingNoteRepository.class);
        getMeetingNoteListService = new GetMeetingNoteListService(meetingNoteRepository);
    }

    @BeforeEach
    void setUpFixtures() {
        user1 = new User(1L);
        user2 = new User(2L);
        meetingNote1 = new MeetingNote("title1", "body1", user1);
        meetingNote2 = new MeetingNote("title2", "body2", user2);
    }


    @Test
    void getMeetingNoteList_TEST() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<MeetingNote> meetingNotes = new PageImpl<>(List.of(meetingNote1, meetingNote2),
                pageable, 2);

        given(meetingNoteRepository.findAllByOrderByIdDesc(pageable))
                .willReturn(meetingNotes);

        MeetingNoteListDto meetingNoteListDto = getMeetingNoteListService.getMeetingNoteList(0, 10);

        assertThat(meetingNoteListDto.meetingNotes()).hasSize(2);
        assertThat(meetingNoteListDto.totalElements()).isEqualTo(2);
        assertThat(meetingNoteListDto.totalPages()).isEqualTo(1);
    }
}