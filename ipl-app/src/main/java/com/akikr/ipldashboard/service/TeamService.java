package com.akikr.ipldashboard.service;

import com.akikr.ipldashboard.model.MatchesResponse;
import com.akikr.ipldashboard.model.TeamResponse;
import com.akikr.ipldashboard.model.TeamsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TeamService
{
	ResponseEntity<TeamsResponse> getAllTeams(int page, int size);

	ResponseEntity<TeamResponse> getTeamData(String teamName, int numberOfMatches);

	ResponseEntity<MatchesResponse> getMatchesForTeamByYear(String teamName, int year);
}
