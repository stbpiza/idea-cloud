package org.ideacloud.dtos;

import java.util.List;

public record KeywordListDto(
        List<KeywordDto> keywords,
        Long totalElements,
        Integer totalPages
) {
}
