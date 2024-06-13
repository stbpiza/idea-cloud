package org.ideacloud.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ideacloud.models.superclass.TimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "keyword_histories")
public class KeywordHistory extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_note_id", nullable = false)
    private MeetingNote meetingNote;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Builder
    public KeywordHistory(Keyword keyword, MeetingNote meetingNote, Integer count) {
        this.keyword = keyword;
        this.meetingNote = meetingNote;
        this.count = count;
    }

    public Long id() {
        return id;
    }

    public Keyword keyword() {
        return keyword;
    }

    public MeetingNote meetingNote() {
        return meetingNote;
    }

    public Integer count() {
        return count;
    }
}
