package com.akikr.ipldashboard.service;

import java.time.LocalDate;
import java.util.List;

import com.akikr.ipldashboard.model.Match;
import com.akikr.ipldashboard.model.Team;
import com.akikr.ipldashboard.repository.MatchRepository;
import com.akikr.ipldashboard.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamService(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    public Team getTeamData(String teamName) {
        Team team = this.teamRepository.findByTeamName(teamName);
        team.setMatches(this.matchRepository.findLatestMatchesByTeam(teamName, 4));
        return team;
    }

    public List<Match> getMatchesForTeamByYear(String teamName, int year) {
        LocalDate startDateOfYear = LocalDate.of(year, 1, 1);
        LocalDate endDateOfYear = LocalDate.of(year, 12, 31);
        return this.matchRepository.getMatchesByTeamBetweenDates(teamName, startDateOfYear, endDateOfYear);
    }
}
