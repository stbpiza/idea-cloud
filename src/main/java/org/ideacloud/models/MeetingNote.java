package org.ideacloud.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ideacloud.models.superclass.TimeEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "meeting_notes")
public class MeetingNote extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "meetingNote")
    private List<KeywordHistory> keywordHistories;


    @Builder
    public MeetingNote(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public MeetingNote(Long id) {
        this.id = id;
    }

    public Long id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String body() {
        return body;
    }

    public User user() {
        return user;
    }

    public List<KeywordHistory> keywordHistories() {
        return keywordHistories;
    }

    public void update(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
