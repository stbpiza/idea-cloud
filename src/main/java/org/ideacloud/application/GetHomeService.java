package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.HomeResultDto;
import org.ideacloud.dtos.KeywordDto;
import org.ideacloud.models.Keyword;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class GetHomeService {

    private final KeywordService keywordService;
    private final TeamService teamService;


    public HomeResultDto getHome(Long userId) {

        Long teamId = teamService.getTeamId(userId);

        return new HomeResultDto(
                keywordService.getOnGoingKeywords(teamId)
                        .stream()
                        .map(this::mapToOnGoingKeywordDto)
                        .toList(),
                keywordService.getAllKeywords(teamId)
                        .stream()
                        .map(this::mapToKeywordDto)
                        .toList()
        );
    }

    private KeywordDto mapToKeywordDto(Keyword keyword) {
        return new KeywordDto(
                keyword.id(),
                keyword.keyword(),
                keyword.priority(),
                keyword.count()
        );
    }

    private HomeResultDto.OnGoingKeywordDto mapToOnGoingKeywordDto(Keyword keyword) {
        return new HomeResultDto.OnGoingKeywordDto(
                keyword.id(),
                keyword.keyword(),
                keyword.priority(),
                keyword.count(),
                keyword.keywordOrders().get(0).order()
        );
    }
}
