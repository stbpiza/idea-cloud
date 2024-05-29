package org.ideacloud.repositories;

import org.ideacloud.models.MeetingNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingNoteRepository extends JpaRepository<MeetingNote, Long> {
}
