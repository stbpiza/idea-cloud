package org.ideacloud.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ideacloud.application.CreateMeetingNoteService;
import org.ideacloud.application.GetMeetingNoteDetailService;
import org.ideacloud.application.GetMeetingNoteListService;
import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.dtos.MeetingNoteDetailDto;
import org.ideacloud.dtos.MeetingNoteListDto;
import org.ideacloud.security.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/meeting-notes")
public class MeetingNoteController {

    private final CreateMeetingNoteService createMeetingNoteService;
    private final GetMeetingNoteListService getMeetingNoteListService;
    private final GetMeetingNoteDetailService getMeetingNoteDetailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createMeetingNote(Authentication authentication,
                                  @Valid @RequestBody MeetingNoteCreateDto dto) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        return createMeetingNoteService.createMeetingNote(dto.title(), dto.body(),
                authUser.id(), dto.keywords());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MeetingNoteListDto listMeetingNotes(@RequestParam Integer page,
                                              @RequestParam Integer size) {
        return getMeetingNoteListService.getMeetingNoteList(page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MeetingNoteDetailDto detail(@PathVariable Long id) {
        return getMeetingNoteDetailService.getMeetingNoteDetail(id);
    }
}
