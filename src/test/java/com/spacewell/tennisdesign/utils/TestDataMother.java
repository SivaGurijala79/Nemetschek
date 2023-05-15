package com.spacewell.tennisdesign.utils;

import com.spacewell.tennisdesign.model.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataMother {

    public static List<Team> testTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team("Team1", Arrays.asList("Player1", "Player2"), "30", true));
        teams.add(new Team("Team2", Arrays.asList("Player3", "Player4"), "15", false));
        return teams;
    }

    public static List<Team> sameNameTestTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team("Team1", Arrays.asList("Player1", "Player2"), "0", true));
        teams.add(new Team("Team1", Arrays.asList("Player3", "Player4"), "0", false));
        return teams;
    }
}
