package org.ideacloud.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ideacloud.application.CreateMeetingNoteService;
import org.ideacloud.application.GetMeetingNoteDetailService;
import org.ideacloud.application.GetMeetingNoteListService;
import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.dtos.MeetingNoteDetailDto;
import org.ideacloud.dtos.MeetingNoteDto;
import org.ideacloud.dtos.MeetingNoteListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(MeetingNoteController.class)
class MeetingNoteControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateMeetingNoteService createMeetingNoteService;

    @MockBean
    private GetMeetingNoteListService getMeetingNoteListService;

    @MockBean
    private GetMeetingNoteDetailService getMeetingNoteDetailService;

    @Test
    @DisplayName("POST /meeting-notes")
    void createMeetingNote() throws Exception {
        MeetingNoteCreateDto dto = new MeetingNoteCreateDto("title", "body", null);

//        given(createMeetingNoteService.createMeetingNote(dto.title(), dto.body(), USER_ID, dto.keywords()))
//                .willReturn("success");

        mockMvc.perform(post("/meeting-notes")
                        .header("Authorization", "Bearer " + userAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(createMeetingNoteService).createMeetingNote(
                eq(dto.title()), eq(dto.body()), eq(USER_ID), eq(dto.keywords()));
    }

    @Test
    @DisplayName("GET /meeting-notes")
    void listMeetingNotes() throws Exception {

        int page = 1;
        int size = 10;
        String title = "title";

        MeetingNoteListDto meetingNoteListDto = new MeetingNoteListDto(
                List.of(new MeetingNoteDto(1L, "title", "body", LocalDateTime.now(), 1L, "user")),
                1L,
                1
        );

        given(getMeetingNoteListService.getMeetingNoteList(page, size))
                .willReturn(meetingNoteListDto);

        mockMvc.perform(get("/meeting-notes")
                        .header("Authorization", "Bearer " + userAccessToken)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(title)));
    }


    @Test
    @DisplayName("GET /meeting-notes/{id}")
    void detail() throws Exception {
        MeetingNoteDetailDto meetingNoteDto = new MeetingNoteDetailDto(
                1L, "title", "body", LocalDateTime.now(), 1L, "user", List.of("keyword")
        );

        given(getMeetingNoteDetailService.getMeetingNoteDetail(1L))
                .willReturn(meetingNoteDto);

        mockMvc.perform(get("/meeting-notes/1")
                        .header("Authorization", "Bearer " + userAccessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }
}