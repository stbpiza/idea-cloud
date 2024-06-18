package org.ideacloud.dtos;

public record KeywordDto(
        Long id,
        String name,
        Integer priority,
        Integer count
) {
}
