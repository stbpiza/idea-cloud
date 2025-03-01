package org.ideacloud.repositories;

import org.ideacloud.models.MeetingNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingNoteRepository extends JpaRepository<MeetingNote, Long> {

    Optional<MeetingNote> findByIdAndTeamId(Long meetingNoteId, Long teamId);

    Page<MeetingNote> findAllByTeamIdOrderByIdDesc(Long teamId, Pageable pageable);
}
