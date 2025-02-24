package org.ideacloud.application;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.MeetingNoteCreateDto.AddKeywordToMeetingNoteDto;
import org.ideacloud.models.Keyword;
import org.ideacloud.models.KeywordHistory;
import org.ideacloud.models.MeetingNote;
import org.ideacloud.repositories.KeywordHistoryRepository;
import org.ideacloud.repositories.KeywordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordHistoryRepository keywordHistoryRepository;
    private final EntityManager em;

    public void addKeywords(Long meetingNoteId, List<AddKeywordToMeetingNoteDto> keywords) {

        List<String> keywordStrings = keywords.stream()
                .map(AddKeywordToMeetingNoteDto::keyword)
                .toList();

        List<Keyword> existingKeywords = keywordRepository.findAllByKeywordIn(keywordStrings);

        List<Keyword> newKeywords = getNewKeywords(keywordStrings, existingKeywords);

        keywordRepository.saveAll(newKeywords);

        List<Keyword> allKeywords = new ArrayList<>();
        allKeywords.addAll(existingKeywords);
        allKeywords.addAll(newKeywords);

        Map<Keyword, Integer> keywordCountMap = getKeywordCountMap(allKeywords, keywords);

        updateKeywordCount(keywordCountMap, meetingNoteId);

        addKeywordHistories(keywordCountMap, meetingNoteId);
    }

    public void updateKeywords(Long meetingNoteId, List<AddKeywordToMeetingNoteDto> keywords) {

        List<KeywordHistory> keywordHistories = keywordHistoryRepository.findAllByMeetingNoteId(meetingNoteId);

        for (KeywordHistory keywordHistory : keywordHistories) {
            keywordHistory.keyword().updateCount(keywordHistory.keyword().count() - keywordHistory.count());
            keywordHistoryRepository.delete(keywordHistory);
        }
        em.flush();

        addKeywords(meetingNoteId, keywords);
    }

    protected List<Keyword> getNewKeywords(List<String> keywordStrings, List<Keyword> existingKeywords) {
        return keywordStrings.stream()
                .filter(keyword -> existingKeywords.stream().noneMatch(k -> k.keyword().equals(keyword)))
                .map(Keyword::new)
                .toList();
    }


    protected Map<Keyword, Integer> getKeywordCountMap(List<Keyword> allKeywords, List<AddKeywordToMeetingNoteDto> keywords) {
        return keywords.stream()
                .collect(Collectors.toMap(
                        keywordDto -> allKeywords.stream()
                                .filter(keyword -> keyword.keyword().equals(keywordDto.keyword()))
                                .findFirst()
                                .orElseThrow(),
                        AddKeywordToMeetingNoteDto::count
                ));
    }

    protected void updateKeywordCount(Map<Keyword, Integer> keywords, Long meetingNoteId) {
        keywords.forEach((keyword, count) -> {
            Integer sum = keywordHistoryRepository.sumCountByKeywordIdWithoutByMeetingNoteId(keyword.id(),
                    meetingNoteId);
            keyword.updateCount( ( sum == null ? 0 : sum ) + count);
        });
    }

    protected void addKeywordHistories(Map<Keyword, Integer> keywordMap, Long meetingNoteId) {
        List<KeywordHistory> keywordHistories = new ArrayList<>();

        for (Map.Entry<Keyword, Integer> entry : keywordMap.entrySet()) {
            keywordHistories.add(KeywordHistory.builder()
                    .keyword(entry.getKey())
                    .meetingNote(new MeetingNote(meetingNoteId))
                    .count(entry.getValue())
                    .build());
        }

        keywordHistoryRepository.saveAll(keywordHistories);
    }

}
