package com.akikr.ipldashboard.controller;

import java.util.List;

import com.akikr.ipldashboard.model.Match;
import com.akikr.ipldashboard.model.Team;
import com.akikr.ipldashboard.service.TeamService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public Iterable<Team> getAllTeams() {
        return this.teamService.getAllTeams();
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        return this.teamService.getTeamData(teamName);
    }

    @GetMapping("/team/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year) {
        return this.teamService.getMatchesForTeamByYear(teamName, year);
    }
}
