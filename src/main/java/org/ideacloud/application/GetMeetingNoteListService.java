package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.MeetingNoteDto;
import org.ideacloud.dtos.MeetingNoteListDto;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class GetMeetingNoteListService {

    private final TeamService teamService;
    private final MeetingNoteRepository meetingNoteRepository;

    public MeetingNoteListDto getMeetingNoteList(Pageable pageable, Long userId) {

        Long teamId = teamService.getTeamId(userId);

        Page<MeetingNote> meetingNotes = meetingNoteRepository.findAllByTeamIdOrderByIdDesc(teamId, pageable);

        return new MeetingNoteListDto(
                meetingNotes.getContent().stream()
                        .map(this::mapToDto)
                        .toList(),
                meetingNotes.getTotalElements(),
                meetingNotes.getTotalPages()
        );
    }


    private MeetingNoteDto mapToDto(MeetingNote meetingNote) {
        return new MeetingNoteDto(
                meetingNote.id(),
                meetingNote.title(),
                meetingNote.body(),
                meetingNote.created(),
                meetingNote.user().id(),
                meetingNote.user().name(),
                meetingNote.getKeywordHistories().stream()
                        .map(kh -> kh.keyword().keyword())
                        .toList()
        );
    }
}
