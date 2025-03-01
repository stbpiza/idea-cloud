package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UpdateMeetingNoteService {

    private final MeetingNoteRepository meetingNoteRepository;
    private final KeywordService keywordService;
    private final TeamService teamService;

    public String updateMeetingNote(
            Long meetingNoteId,
            MeetingNoteCreateDto dto,
            Long userId
    ) {

        Long teamId = teamService.getTeamId(userId);

        MeetingNote meetingNote = meetingNoteRepository.findById(meetingNoteId).orElseThrow();

        meetingNote.update(dto.title(), dto.body());

        keywordService.updateKeywords(meetingNoteId, dto.keywords(), teamId);

        return "success";
    }

}
