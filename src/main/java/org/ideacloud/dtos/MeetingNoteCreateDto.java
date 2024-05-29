package org.ideacloud.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record MeetingNoteCreateDto(
        String title,
        String body,
        List<AddKeywordToMeetingNoteDto> keywords
) {

    public record AddKeywordToMeetingNoteDto(
            @NotBlank
            String keyword,
            @Min(1)
            int count
    ) {
    }
}
