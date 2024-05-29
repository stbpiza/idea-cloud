package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.models.Keyword;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.repositories.KeywordHistoryRepository;
import org.ideacloud.repositories.KeywordRepository;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Service
public class CreateMeetingNoteService {

    private final MeetingNoteRepository meetingNoteRepository;
    private final KeywordRepository keywordRepository;
    private final KeywordHistoryRepository keywordHistoryRepository;

    public void createMeetingNote(String title, String body, Long userId,
                                  List<MeetingNoteCreateDto.AddKeywordToMeetingNoteDto> keywords) {
        MeetingNote meetingNote = MeetingNote.builder()
                .title(title)
                .body(body)
                .userId(userId)
                .build();

        meetingNoteRepository.save(meetingNote);

        addKeywords(keywords, meetingNote);
    }

    protected void addKeywords(List<MeetingNoteCreateDto.AddKeywordToMeetingNoteDto> keywords, MeetingNote meetingNote) {

        List<String> keywordStrings = keywords.stream()
                .map(MeetingNoteCreateDto.AddKeywordToMeetingNoteDto::keyword)
                .toList();

        Set<Keyword> existingKeyword = keywordRepository.findAllByKeywordIn(keywordStrings);

        List<Keyword> newKeywords = keywordStrings.stream()
                .filter(keyword -> existingKeyword.stream().noneMatch(k -> k.getKeyword().equals(keyword)))
                .map(Keyword::new)
                .toList();

        keywordRepository.saveAll(newKeywords);
    }

}
