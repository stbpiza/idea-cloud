package org.ideacloud.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ideacloud.models.superclass.TimeEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "keywords")
public class Keyword extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "keyword")
    private List<KeywordHistory> keywordHistories;


    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public Keyword(Long id) {
        this.id = id;
    }

    public Long id() {
        return id;
    }

    public String keyword() {
        return keyword;
    }

    public List<KeywordHistory> keywordHistories() {
        return keywordHistories;
    }
}
