package org.ideacloud.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ideacloud.application.CreateMeetingNoteService;
import org.ideacloud.application.GetMeetingNoteDetailService;
import org.ideacloud.application.GetMeetingNoteListService;
import org.ideacloud.application.UpdateMeetingNoteService;
import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.dtos.MeetingNoteDetailDto;
import org.ideacloud.dtos.MeetingNoteListDto;
import org.ideacloud.security.AuthUser;
import org.ideacloud.utils.PaginationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "03.MeetingNote", description = "회의록 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/meeting-notes")
public class MeetingNoteController {

    private final CreateMeetingNoteService createMeetingNoteService;
    private final GetMeetingNoteListService getMeetingNoteListService;
    private final GetMeetingNoteDetailService getMeetingNoteDetailService;
    private final UpdateMeetingNoteService updateMeetingNoteService;

    @Operation(summary = "회의록 등록", description = "회의록을 등록합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createMeetingNote(Authentication authentication,
                                  @Valid @RequestBody MeetingNoteCreateDto dto) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        return createMeetingNoteService.createMeetingNote(dto.title(), dto.body(),
                authUser.id(), dto.keywords());
    }

    @Operation(summary = "회의록 목록 조회", description = "회의록 목록을 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MeetingNoteListDto listMeetingNotes(Authentication authentication,
                                               @RequestParam Integer page,
                                              @RequestParam Integer size) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        return getMeetingNoteListService.getMeetingNoteList(PaginationUtil.makePageable(page, size), authUser.id());
    }

    @Operation(summary = "회의록 상세 조회", description = "회의록 상세를 조회합니다.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MeetingNoteDetailDto detail(Authentication authentication,
                                       @PathVariable Long id) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        return getMeetingNoteDetailService.getMeetingNoteDetail(id, authUser.id());
    }

    @Operation(summary =  "회의록 수정", description = "회의록을 수정합니다.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateMeetingNote(Authentication authentication,
                                    @PathVariable Long id,
                                    @Valid @RequestBody MeetingNoteCreateDto dto) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        return updateMeetingNoteService.updateMeetingNote(id, dto, authUser.id());
    }
}
