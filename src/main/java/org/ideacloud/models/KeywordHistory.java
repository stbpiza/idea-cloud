package org.ideacloud.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ideacloud.models.superclass.TimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "keyword_histories")
public class KeywordHistory extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "meeting_note_id", nullable = false)
    private Long meetingNoteId;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Builder
    public KeywordHistory(Long keywordId, Long meetingNoteId, Integer count) {
        this.keywordId = keywordId;
        this.meetingNoteId = meetingNoteId;
        this.count = count;
    }
}
