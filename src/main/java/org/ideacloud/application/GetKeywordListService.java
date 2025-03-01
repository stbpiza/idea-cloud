package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.dtos.KeywordDto;
import org.ideacloud.dtos.KeywordListDto;
import org.ideacloud.models.Keyword;
import org.ideacloud.repositories.KeywordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class GetKeywordListService {

    private final TeamService teamService;
    private final KeywordRepository keywordRepository;

    public KeywordListDto getKeywordList(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size);

        Long teamId = teamService.getTeamId(userId);

        Page<Keyword> keywords = keywordRepository.findAllByTeamIdOrderByIdDesc(teamId, pageable);

        return new KeywordListDto(
                keywords.getContent().stream()
                        .map(this::mapToDto)
                        .toList(),
                keywords.getTotalElements(),
                keywords.getTotalPages()
        );
    }

    private KeywordDto mapToDto(Keyword keyword) {
        return new KeywordDto(
                keyword.id(),
                keyword.keyword(),
                keyword.priority(),
                keyword.count()
        );
    }
}
