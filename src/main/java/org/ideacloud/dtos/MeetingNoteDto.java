package org.ideacloud.dtos;

import java.time.LocalDateTime;

public record MeetingNoteDto(
        Long id,
        String title,
        String body,
        LocalDateTime createdAt,
        Long userId,
        String userName
) {

}
