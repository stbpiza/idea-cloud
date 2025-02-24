package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.models.User;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CreateMeetingNoteService {

    private final MeetingNoteRepository meetingNoteRepository;
    private final KeywordService keywordService;

    public String createMeetingNote(String title, String body, Long userId,
                                  List<MeetingNoteCreateDto.AddKeywordToMeetingNoteDto> keywords) {
        MeetingNote meetingNote = MeetingNote.builder()
                .title(title)
                .body(body)
                .user(new User(userId))
                .build();

        meetingNoteRepository.save(meetingNote);

        keywordService.addKeywords(meetingNote.id(), keywords);

        return "success";
    }

}
