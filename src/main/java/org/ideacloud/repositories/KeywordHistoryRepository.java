package org.ideacloud.repositories;

import org.ideacloud.models.KeywordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeywordHistoryRepository extends JpaRepository<KeywordHistory, Long> {

    @Query(value =
            "SELECT sum(kh.count) " +
            "FROM KeywordHistory kh " +
            "WHERE kh.keyword.id = :keywordId " +
            "AND kh.meetingNote.id != :meetingNoteId "
    )
    Integer sumCountByKeywordIdWithoutByMeetingNoteId(Long keywordId, Long meetingNoteId);

}
