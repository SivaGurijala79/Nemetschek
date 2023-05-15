package com.spacewell.tenissdesign.repo;


import com.spacewell.tenissdesign.exceptions.ExceptionType;
import com.spacewell.tenissdesign.exceptions.NoTeamFound;
import com.spacewell.tenissdesign.exceptions.StartMatchException;
import com.spacewell.tenissdesign.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PlayersDataRepo {

    Logger logger = LoggerFactory.getLogger(PlayersDataRepo.class);
    private List<Team> teams = new ArrayList<>();

    public void addTeams(List<Team> playingTeams) {
        if (teams.isEmpty()) {
            teams.addAll(playingTeams);
        } else {
            throw new StartMatchException("Can't add new teams when one match is going on", ExceptionType.MATCH_IN_PROGRESS);
        }
    }

    public void updateScore(Team scoredTeam) {
        Optional<Team> teamToUpdateScore = teams.stream().filter(team -> team.getTeamName().equalsIgnoreCase(scoredTeam.getTeamName())).findFirst();
        if (teamToUpdateScore.isPresent()) {
            teamToUpdateScore.get().setPointsScored(scoredTeam.getPointsScored());
        } else {
            throw new NoTeamFound("No team is playing with name : " + scoredTeam.getTeamName());
        }
    }

    public void closeMatch() {
        teams.clear();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void updateServe(String teamName, boolean servingStatus) {
        Optional<Team> team2 = teams.stream().filter(team -> team.getTeamName().equals(teamName)).findFirst();
        team2.ifPresent(team -> team.setServing(servingStatus));
    }
}
