package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.MeetingNoteDetailDto;
import org.ideacloud.exceptions.MeetingNoteNotFoundException;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class GetMeetingNoteDetailService {

    private final MeetingNoteRepository meetingNoteRepository;

    public MeetingNoteDetailDto getMeetingNoteDetail(Long meetingNoteId) {
        return meetingNoteRepository.findById(meetingNoteId)
                .map(meetingNote -> new MeetingNoteDetailDto(
                        meetingNote.id(),
                        meetingNote.title(),
                        meetingNote.body(),
                        meetingNote.created(),
                        meetingNote.user().id(),
                        meetingNote.user().name(),
                        meetingNote.keywordHistories()
                                .stream().map(
                                        keywordHistory -> keywordHistory.keyword().keyword()
                                ).toList()
                ))
                .orElseThrow(() -> new MeetingNoteNotFoundException(meetingNoteId));
    }
}
