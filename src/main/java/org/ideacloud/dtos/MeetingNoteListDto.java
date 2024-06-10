package org.ideacloud.dtos;

import java.util.List;

public record MeetingNoteListDto(
        List<MeetingNoteDto> meetingNotes,
        Long totalElements,
        Integer totalPages
) {
}
