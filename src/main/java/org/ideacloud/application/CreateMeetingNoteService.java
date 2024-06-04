package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.models.Keyword;
import org.ideacloud.models.KeywordHistory;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.repositories.KeywordHistoryRepository;
import org.ideacloud.repositories.KeywordRepository;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

        Map<Keyword, Integer> keywordMap = addKeywords(keywords, meetingNote);

        addKeywordHistories(keywordMap, meetingNote);
    }

    protected Map<Keyword, Integer> addKeywords(List<MeetingNoteCreateDto.AddKeywordToMeetingNoteDto> keywords, MeetingNote meetingNote) {

        List<String> keywordStrings = keywords.stream()
                .map(MeetingNoteCreateDto.AddKeywordToMeetingNoteDto::keyword)
                .toList();

        List<Keyword> existingKeyword = keywordRepository.findAllByKeywordIn(keywordStrings);

        List<Keyword> newKeywords = keywordStrings.stream()
                .filter(keyword -> existingKeyword.stream().noneMatch(k -> k.getKeyword().equals(keyword)))
                .map(Keyword::new)
                .toList();

        keywordRepository.saveAll(newKeywords);

        Map<Keyword, Integer> keywordCountMap = new HashMap<>();

        for (MeetingNoteCreateDto.AddKeywordToMeetingNoteDto dto : keywords) {
            boolean isPut = false;
            for (Keyword keyword : existingKeyword) {
                if (keyword.getKeyword().equals(dto.keyword())) {
                    keywordCountMap.put(keyword, dto.count());
                    isPut = true;
                    break;
                }
            }
            if (isPut) break;
            for (Keyword keyword : existingKeyword) {
                if (keyword.getKeyword().equals(dto.keyword())) {
                    keywordCountMap.put(keyword, dto.count());
                    break;
                }
            }
        }

        return keywordCountMap;
    }

    protected void addKeywordHistories(Map<Keyword, Integer> keywordMap, MeetingNote meetingNote) {
        List<KeywordHistory> keywordHistories = new ArrayList<>();

        for (Map.Entry<Keyword, Integer> entry : keywordMap.entrySet()) {
            keywordHistories.add(KeywordHistory.builder()
                    .keyword(entry.getKey())
                    .meetingNote(meetingNote)
                    .count(entry.getValue())
                    .build());
        }

        keywordHistoryRepository.saveAll(keywordHistories);
    }

}
