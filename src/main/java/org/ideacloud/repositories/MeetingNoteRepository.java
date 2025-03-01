package org.ideacloud.repositories;

import org.ideacloud.models.MeetingNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingNoteRepository extends JpaRepository<MeetingNote, Long> {

    Page<MeetingNote> findAllByTeamIdOrderByIdDesc(Long teamId, Pageable pageable);
}
