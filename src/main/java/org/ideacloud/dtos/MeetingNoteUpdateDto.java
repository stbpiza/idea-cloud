package org.ideacloud.dtos;

import java.util.List;

public record MeetingNoteUpdateDto(
        Long id,
        String title,
        String body,
        List<MeetingNoteCreateDto.AddKeywordToMeetingNoteDto> keywords
) {
}
