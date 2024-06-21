package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.MeetingNoteCreateDto.AddKeywordToMeetingNoteDto;
import org.ideacloud.models.Keyword;
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
public class CreateKeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordHistoryRepository keywordHistoryRepository;

    public Map<Keyword, Integer> addKeywords(List<AddKeywordToMeetingNoteDto> keywords) {

        List<String> keywordStrings = keywords.stream()
                .map(AddKeywordToMeetingNoteDto::keyword)
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

    public void updateKeywordCount(Map<Keyword, Integer> keywords, Long meetingNoteId) {
        keywords.forEach((keyword, count) -> {
                    keyword.updateCount(
                            keywordHistoryRepository.sumCountByKeywordIdWithoutByMeetingNoteId(keyword.id(), meetingNoteId)
                            + count);
        });
    }

}
