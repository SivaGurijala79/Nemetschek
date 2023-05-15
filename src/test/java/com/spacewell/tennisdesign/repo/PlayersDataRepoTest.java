package com.spacewell.tennisdesign.repo;

import com.spacewell.tennisdesign.model.Team;
import org.junit.Test;

import java.util.Arrays;

import static com.spacewell.tennisdesign.utils.TestDataMother.testTeams;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayersDataRepoTest {

    PlayersDataRepo playersDataRepo = new PlayersDataRepo();

    @Test
    public void addTeams() {
        playersDataRepo.addTeams(testTeams());
        assertEquals(2, playersDataRepo.getTeams().size());
    }

    @Test
    public void updateScore() {
        playersDataRepo.addTeams(testTeams());
        playersDataRepo.updateScore(new Team("Team1", Arrays.asList("Person1", "Person2"), "40", true));
        assertTrue(playersDataRepo.getTeams().stream().anyMatch(team -> team.getTeamName().equals("Team1") && team.getPointsScored().equals("40")));
    }

    @Test
    public void closeMatch() {
        playersDataRepo.addTeams(testTeams());
        assertEquals(2, playersDataRepo.getTeams().size());
        playersDataRepo.closeMatch();
        assertEquals(0, playersDataRepo.getTeams().size());
    }

    @Test
    public void updateServe() {
        playersDataRepo.addTeams(testTeams());
        playersDataRepo.updateServe("Team2", true);
        assertTrue(playersDataRepo.getTeams().stream().anyMatch(team -> team.getTeamName().equals("Team2") && team.isServing()));
    }
}