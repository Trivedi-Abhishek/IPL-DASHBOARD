package com.springapp.ipldashboard.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.ipldashboard.model.Match;
import com.springapp.ipldashboard.model.Team;
import com.springapp.ipldashboard.repository.MatchRepository;
import com.springapp.ipldashboard.repository.TeamRepository;

@RestController
@CrossOrigin
public class TeamController {
    
    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    
    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository=matchRepository;
    }

    @GetMapping("/team")
    Iterable<Team> getAllTeam(){
        return teamRepository.findAll();
    }
    @GetMapping("/team/{teamName}")
    Team getTeamName(@PathVariable String teamName){
        Team team= teamRepository.findByTeamName(teamName);
        team.setMatches(matchRepository.findLatestMatchesByTeam(teamName, 4));
        return team;
    }
    @GetMapping("/team/{teamName}/matches")
    List<Match> getByTeamBetweenDates(@PathVariable String teamName,@RequestParam int year){
        LocalDate startDate=LocalDate.of(year, 1, 1);
        LocalDate endDate=LocalDate.of(year+1, 1, 1);

        return this.matchRepository.getByTeamBetweenDates(teamName, startDate, endDate);
    }
}
