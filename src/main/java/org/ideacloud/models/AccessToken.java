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
@Table(name = "access_tokens")
public class AccessToken extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public AccessToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public Long id() {
        return id;
    }

    public String token() {
        return token;
    }

    public Long userId() {
        return userId;
    }
}
