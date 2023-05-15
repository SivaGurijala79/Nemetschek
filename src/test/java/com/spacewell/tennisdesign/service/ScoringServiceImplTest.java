package com.spacewell.tenissdesign.service;

import com.spacewell.tenissdesign.exceptions.StartMatchException;
import com.spacewell.tenissdesign.repo.PlayersDataRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static com.spacewell.tenissdesign.utils.TestDataMother.sameNameTestTeams;
import static com.spacewell.tenissdesign.utils.TestDataMother.testTeams;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScoringServiceImplTest {

    @Mock
    private PlayersDataRepo playersDataRepo;

    @InjectMocks
    private ScoringServiceImpl scoringService;

    @Before
    public void init() {
        when(playersDataRepo.getTeams()).thenReturn(testTeams());
    }

    @Test
    public void startMatch_startMatchException() {
        try {
            scoringService.startMatch(new ArrayList<>());
            fail("StartMatchException expected as no of teams are 0");
        } catch (StartMatchException e) {
            assertEquals("Teams should be 2.", e.getMessage());
        }
        try {
            scoringService.startMatch(sameNameTestTeams());
            fail("StartMatchException expected as teams have same name");
        } catch (StartMatchException e) {
            assertEquals("Teams can't have same name", e.getMessage());
        }

    }

    @Test
    public void test_StartMatch() {
        scoringService.startMatch(testTeams());
        Mockito.verify(playersDataRepo).addTeams(anyList());
    }

    @Test
    public void test_setServe() {
        scoringService.setServe("Team1");
        Mockito.verify(playersDataRepo).updateServe("Team1", true);
        Mockito.verify(playersDataRepo).updateServe("Team2", false);
    }

    @Test
    public void test_updateScore() {
        ReflectionTestUtils.setField(scoringService, "playersDataRepo", new PlayersDataRepo());
        scoringService.startMatch(testTeams());
        String score = scoringService.updateScore("Team1");
        assertEquals("40:15", score);
        score = scoringService.updateScore("Team2");
        assertEquals("40:30", score);
    }

    @Test
    public void test_getScore() {
        String score = scoringService.getScore();
        assertEquals("30:15", score);
        Mockito.verify(playersDataRepo).getTeams();
    }
}