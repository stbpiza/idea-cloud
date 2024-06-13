package org.ideacloud.exceptions;

public class MeetingNoteNotFoundException extends RuntimeException {

    public MeetingNoteNotFoundException(Long id) {
        super("MeetingNote with id " + id + " not found");
    }
}
