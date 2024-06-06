package org.ideacloud.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ideacloud.application.CreateMeetingNoteService;
import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingNoteController.class)
class MeetingNoteControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateMeetingNoteService createMeetingNoteService;

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

}