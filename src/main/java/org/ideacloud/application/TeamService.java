package org.ideacloud.application;

import lombok.RequiredArgsConstructor;
import org.ideacloud.exceptions.TeamNotFoundException;
import org.ideacloud.models.Team;
import org.ideacloud.models.TeamMember;
import org.ideacloud.models.TeamRole;
import org.ideacloud.models.User;
import org.ideacloud.repositories.TeamMemberRepository;
import org.ideacloud.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    public void createTeam(User user) {

        Team team = Team.builder()
                .name(user.name())
                .build();

        teamRepository.save(team);

        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .user(user)
                .teamRole(TeamRole.MANAGER)
                .build();

        teamMemberRepository.save(teamMember);
    }

    public Long getTeamId(Long userId) {
        List<TeamMember> myTeams = teamMemberRepository.findByUserId(userId);

        if (myTeams.isEmpty()) {
            throw new TeamNotFoundException("Team not found");
        }

        return myTeams.get(0).team().id();
    }
}
