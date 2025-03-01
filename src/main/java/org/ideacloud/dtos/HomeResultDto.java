package org.ideacloud.dtos;

import java.util.List;

public record HomeResultDto(
        List<OnGoingKeywordDto> onGoingKeywords,
        List<KeywordDto> keywords
) {

    public record OnGoingKeywordDto(
            Long id,
            String name,
            Integer priority,
            Integer count,
            Integer order
    ) {
    }

}
