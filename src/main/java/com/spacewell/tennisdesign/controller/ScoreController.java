package com.spacewell.tenissdesign.controller;

import com.spacewell.tenissdesign.model.Team;
import com.spacewell.tenissdesign.service.ScoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//9381124194

@RestController
public class ScoreController {
    @Autowired
    private ScoringService scoringService;

    @PostMapping("/start-match")
    public void startMatch(@RequestBody List<Team> teams) {
        scoringService.startMatch(teams);
    }

    @PostMapping("/set-serve")
    public void startMatch(@PathVariable("team-name") String teamName) {
        scoringService.setServe(teamName);
    }

    @PutMapping("/update-score/{team-name}")
    public void updateScore(@PathVariable("team-name") String teamName) {
        scoringService.updateScore(teamName);
    }

    @GetMapping("/score")
    public String getScore() {
        return scoringService.getScore();
    }

    @GetMapping("/winner")
    public Team getWinner() {
        return scoringService.getWinner();
    }

    @DeleteMapping("/close-match")
    public void closeMatch() {
        scoringService.closeMatch();
    }

}
