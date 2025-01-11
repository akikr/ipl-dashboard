package com.akikr.ipldashboard.controller;

import com.akikr.ipldashboard.model.MatchesResponse;
import com.akikr.ipldashboard.model.TeamResponse;
import com.akikr.ipldashboard.model.TeamsResponse;
import com.akikr.ipldashboard.service.MatchService;
import com.akikr.ipldashboard.service.TeamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@formatter:off
@CrossOrigin(originPatterns = "*" ,
		methods = { RequestMethod.GET, RequestMethod.OPTIONS },
		allowedHeaders = "*",
		allowCredentials = "true",
		maxAge = 3600)
//@formatter:on
@RestController
@Tag(name = "DashboardController", description = "Endpoints for all IplDashboardApplication APIs")
public class DashboardController
{
	private final TeamService teamService;
	private final MatchService matchService;

	public DashboardController(TeamService teamService, MatchService matchService)
	{
		this.teamService = teamService;
		this.matchService = matchService;
	}

	@GetMapping(path = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TeamsResponse> getAllTeams(
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size)
	{
		return teamService.getAllTeams(page, size);
	}

	@GetMapping(path = "/matches", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MatchesResponse> getAllMatches(
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size)
	{
		return matchService.getAllMatches(page, size);
	}

	@GetMapping(path = "/team/{teamName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TeamResponse> getTeam(
			@PathVariable(value = "teamName") String teamName,
			@RequestParam(value = "numberOfMatches", defaultValue = "4", required = false) int numberOfMatches)
	{
		return teamService.getTeamData(teamName, numberOfMatches);
	}

	@GetMapping(path = "/team/{teamName}/matches", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MatchesResponse> getMatchesForTeam(
			@PathVariable(value = "teamName") String teamName,
			@RequestParam(value = "year") int year)
	{
		return teamService.getMatchesForTeamByYear(teamName, year);
	}
}
