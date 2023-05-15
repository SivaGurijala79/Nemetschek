package com.spacewell.tennisdesign.service;

import com.spacewell.tennisdesign.exceptions.ExceptionType;
import com.spacewell.tennisdesign.exceptions.NoTeamFound;
import com.spacewell.tennisdesign.exceptions.StartMatchException;
import com.spacewell.tennisdesign.model.Team;
import com.spacewell.tennisdesign.repo.PlayersDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoringServiceImpl implements ScoringService {

    Logger logger = LoggerFactory.getLogger(ScoringServiceImpl.class);

    @Autowired
    private PlayersDataRepo playersDataRepo;

    @Override
    public void startMatch(List<Team> playingTeams) {
        if (playingTeams.size() != 2) {
            throw new StartMatchException("Teams should be 2.", ExceptionType.TEAMS_SIZE);
        }
        if (playingTeams.get(0).getTeamName().equalsIgnoreCase(playingTeams.get(1).getTeamName())) {
            throw new StartMatchException("Teams can't have same name", ExceptionType.SAME_NAME);
        }

        playersDataRepo.addTeams(playingTeams);
    }

    public void setServe(String servingTeamName) {
        List<Team> teams = playersDataRepo.getTeams();
        teams.forEach(team -> {
            if (team.getTeamName().equalsIgnoreCase(servingTeamName))
                playersDataRepo.updateServe(servingTeamName, true);
            else
                playersDataRepo.updateServe(team.getTeamName(), false);
        });
    }

    @Override
    public String updateScore(String scoredTeamName) {
        List<Team> playingTeams = playersDataRepo.getTeams();
        Optional<Team> scoredTeamOpt = playingTeams.stream().filter(team -> team.getTeamName().equalsIgnoreCase(scoredTeamName)).findFirst();
        if (scoredTeamOpt.isPresent()) {
            Team scoredTeam = scoredTeamOpt.get();
            String score = scoredTeam.getPointsScored();
            if (scoredTeam.isServing()) {
                if (isNumber(score)) {
                    int scoreInt = Integer.parseInt(score);
                    if (scoreInt == 0 || scoreInt == 15)
                        scoredTeam.setPointsScored(String.valueOf(scoreInt + 15));
                    else if (scoreInt == 30)
                        scoredTeam.setPointsScored(String.valueOf(scoreInt + 10));
                    else
                        scoredTeam.setPointsScored("A");
                } else {
                    logger.info("You won");
                    scoredTeam.setPointsScored("W");
                }
            } else {
                if (isNumber(score)) {
                    int scoreInt = Integer.parseInt(score);
                    if (scoreInt == 0 || scoreInt == 15)
                        scoredTeam.setPointsScored(String.valueOf(scoreInt + 15));
                    else if (scoreInt == 30)
                        scoredTeam.setPointsScored(String.valueOf(scoreInt + 10));
                    else {
                        Optional<Team> servingTeamOpt = playingTeams.stream().filter(team -> !team.getTeamName().equalsIgnoreCase(scoredTeamName)).findFirst();
                        Team servingTeam = servingTeamOpt.get();
                        if (servingTeam.getPointsScored().equals("A")) {
                            servingTeam.setPointsScored("40");
                            playersDataRepo.updateScore(servingTeam);
                            return getScore();
                        }
                    }
                } else {
                    logger.info("You won");
                    scoredTeam.setPointsScored("W");
                }
            }
            playersDataRepo.updateScore(scoredTeam);
        } else {
            throw new NoTeamFound("No team found with name : " + scoredTeamName);
        }
        return getScore();
    }

    private boolean isNumber(String score) {
        try {
            Integer.parseInt(score);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getScore() {
        List<Team> playingTeams = playersDataRepo.getTeams();
        Optional<Team> servingTeam = playingTeams.stream().filter(Team::isServing).findFirst();
        Optional<Team> receivingTeam = playingTeams.stream().filter(team -> !team.isServing()).findFirst();
        return servingTeam.get().getPointsScored() + ":" + receivingTeam.get().getPointsScored();
    }

    @Override
    public Team getWinner() {
        List<Team> playingTeams = playersDataRepo.getTeams();
        Optional<Team> wonTeam = playingTeams.stream().filter(team -> team.getPointsScored().equals("W")).findFirst();
        return wonTeam.orElse(null);
    }

    @Override
    public void closeMatch() {

    }
}
