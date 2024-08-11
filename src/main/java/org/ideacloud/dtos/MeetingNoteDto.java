package org.ideacloud.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record MeetingNoteDto(
        Long id,
        String title,
        String body,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created,
        Long userId,
        String userName,
        List<String> keywords
) {

}
