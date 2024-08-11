package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.MeetingNoteCreateDto;
import org.ideacloud.models.Keyword;
import org.ideacloud.models.KeywordHistory;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.models.User;
import org.ideacloud.repositories.KeywordHistoryRepository;
import org.ideacloud.repositories.KeywordRepository;
import org.ideacloud.repositories.MeetingNoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CreateMeetingNoteService {

    private final MeetingNoteRepository meetingNoteRepository;
    private final KeywordRepository keywordRepository;
    private final KeywordHistoryRepository keywordHistoryRepository;
    private final CreateKeywordService createKeywordService;

    public String createMeetingNote(String title, String body, Long userId,
                                  List<MeetingNoteCreateDto.AddKeywordToMeetingNoteDto> keywords) {
        MeetingNote meetingNote = MeetingNote.builder()
                .title(title)
                .body(body)
                .user(new User(userId))
                .build();

        meetingNoteRepository.save(meetingNote);

        Map<Keyword, Integer> keywordMap = addKeywords(keywords);

        createKeywordService.updateKeywordCount(keywordMap, meetingNote.id());

        addKeywordHistories(keywordMap, meetingNote);

        return "success";
    }

    protected Map<Keyword, Integer> addKeywords(List<MeetingNoteCreateDto.AddKeywordToMeetingNoteDto> keywords) {

        List<String> keywordStrings = keywords.stream()
                .map(MeetingNoteCreateDto.AddKeywordToMeetingNoteDto::keyword)
                .toList();

        List<Keyword> existingKeywords = keywordRepository.findAllByKeywordIn(keywordStrings);

        List<Keyword> newKeywords = getNewKeywords(keywordStrings, existingKeywords);

        keywordRepository.saveAll(newKeywords);

        List<Keyword> allKeywords = new ArrayList<>();
        allKeywords.addAll(existingKeywords);
        allKeywords.addAll(newKeywords);

        return getKeywordCountMap(allKeywords, keywords);
    }

    protected List<Keyword> getNewKeywords(List<String> keywordStrings, List<Keyword> existingKeywords) {
        return keywordStrings.stream()
                .filter(keyword -> existingKeywords.stream().noneMatch(k -> k.keyword().equals(keyword)))
                .map(Keyword::new)
                .toList();
    }

    protected Map<Keyword, Integer> getKeywordCountMap(List<Keyword> allKeywords, List<MeetingNoteCreateDto.AddKeywordToMeetingNoteDto> keywords) {
        Map<Keyword, Integer> keywordCountMap = new LinkedHashMap<>();
        for (MeetingNoteCreateDto.AddKeywordToMeetingNoteDto keyword : keywords) {
            keywordCountMap.put(
                    allKeywords.stream()
                            .filter(k -> k.keyword().equals(keyword.keyword()))
                            .findFirst()
                            .orElseThrow(),
                    keyword.count()
            );
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
