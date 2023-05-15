package com.spacewell.tennisdesign.service;

import com.spacewell.tennisdesign.model.Team;

import java.util.List;

public interface ScoringService {
    void startMatch(List<Team> playingTeams);

    String updateScore(String scoredTeamName);

    void setServe(String scoredTeamName);

    String getScore();

    Team getWinner();

    void closeMatch();
}
