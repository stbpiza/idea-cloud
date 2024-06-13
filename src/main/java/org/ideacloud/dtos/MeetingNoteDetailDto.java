package org.ideacloud.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record MeetingNoteDetailDto(
        Long id,
        String title,
        String body,
        LocalDateTime created,
        Long userId,
        String userName,
        List<String> keywords
) {
}
