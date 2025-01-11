package com.akikr.ipldashboard.service;

import com.akikr.ipldashboard.model.MatchesResponse;
import com.akikr.ipldashboard.repo.Match;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MatchService
{
	ResponseEntity<MatchesResponse> getAllMatches(int page, int size);

	List<Match> getAllMatches(int page, int size, Sort sortBy);

	List<Match> getLatestMatchesByTeam(String teamName, int limit);

	List<Match> getMatchesForTeamByYear(String teamName, LocalDate startDateOfYear, LocalDate endDateOfYear);
}
