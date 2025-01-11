package com.akikr.ipldashboard.utils;

import com.akikr.ipldashboard.model.MatchResponse;
import com.akikr.ipldashboard.model.PageableResponse;
import com.akikr.ipldashboard.model.TeamResponse;
import com.akikr.ipldashboard.repo.Match;
import com.akikr.ipldashboard.repo.Team;
import org.springframework.data.domain.Page;

import java.util.Objects;

public class ResponseMapper
{
	public static PageableResponse mapToPageableResponse(Page<?> page)
	{
		return new PageableResponse(page.getTotalElements(), page.getTotalPages(), page.getSize(), page.getNumber());
	}

	public static TeamResponse toTeamResponse(Team team)
	{
		return new TeamResponse(
				team.getId(),
				team.getTeamName(),
				team.getTotalMatches(),
				team.getTotalWins(),
				(Objects.nonNull(team.getMatches())) ? team.getMatches() : null);
	}

	public static MatchResponse toMatchResponse(Match match)
	{
		return new MatchResponse(
				match.getId(),
				match.getCity(),
				match.getDate(),
				match.getPlayerOfMatch(),
				match.getVenue(),
				match.getTeam1(),
				match.getTeam2(),
				match.getTossWinner(),
				match.getTossDecision(),
				match.getMatchWinner(),
				match.getResult(),
				match.getResultMargin(),
				match.getUmpire1(),
				match.getUmpire2());
	}
}
