package org.ideacloud.repositories;

import org.ideacloud.models.MeetingNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingNoteRepository extends JpaRepository<MeetingNote, Long> {

    Page<MeetingNote> findAllByOrderByIdDesc(Pageable pageable);
}
