package org.ideacloud.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.ideacloud.models.superclass.TimeEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "teams")
public class Team extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "current_member_count", nullable = false)
    private int currentMemberCount;

    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers;

    public Long id() {
        return id;
    }

    @Builder
    public Team(String name) {
        this.name = name;
        this.description = name;
        this.currentMemberCount = currentMemberCount = 0;
    }

}
