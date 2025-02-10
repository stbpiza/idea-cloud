package org.ideacloud.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record MeetingNoteUpdateDto(
        Long id,
        String title,
        String body,
        List<UpdateKeywordToMeetingNoteDto> keywords
) {

    public record UpdateKeywordToMeetingNoteDto(
            Long id,
            @NotBlank
            String keyword,
            @Min(1)
            int count
    ){
    }
}
