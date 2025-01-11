package com.akikr.ipldashboard.service;

import com.akikr.ipldashboard.model.MatchResponse;
import com.akikr.ipldashboard.model.MatchesResponse;
import com.akikr.ipldashboard.model.PageableResponse;
import com.akikr.ipldashboard.model.TeamResponse;
import com.akikr.ipldashboard.model.TeamsResponse;
import com.akikr.ipldashboard.repo.Match;
import com.akikr.ipldashboard.repo.Team;
import com.akikr.ipldashboard.repo.TeamRepository;
import com.akikr.ipldashboard.utils.DataNotFoundException;
import com.akikr.ipldashboard.utils.ResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService
{
	private final TeamRepository teamRepository;
	private final MatchService matchService;

	public TeamServiceImpl(TeamRepository teamRepository, MatchService matchService)
	{
		this.teamRepository = teamRepository;
		this.matchService = matchService;
	}

	@Override
	public ResponseEntity<TeamsResponse> getAllTeams(int page, int size)
	{
		log.info("Get teams data for page:[{}] and size:[{}]", page, size);
		Page<Team> teamPage = teamRepository.findAll(PageRequest.of(page, size));
		PageableResponse pages = ResponseMapper.mapToPageableResponse(teamPage);
		//@formatter:off
		List<TeamResponse> teams = teamPage.get()
				.filter(Objects::nonNull)
				.map(ResponseMapper::toTeamResponse)
				.toList();

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(new TeamsResponse(teams, pages));
		//@formatter:on
	}

	@Override
	public ResponseEntity<TeamResponse> getTeamData(String teamName, int numberOfMatches)
	{
		log.info("Get the data for team:[{}] for {} matches", teamName, numberOfMatches);
		try
		{
			//@formatter:off
			Team team = validateAndGetTeam(teamName);
			List<Match> matches = matchService.getLatestMatchesByTeam(teamName, numberOfMatches);
			team.setMatches(matches);

			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(ResponseMapper.toTeamResponse(team));
			//@formatter:on
		}
		catch (DataNotFoundException e)
		{
			log.error("Given team:[{}] NOT found", teamName);
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<MatchesResponse> getMatchesForTeamByYear(String teamName, int year)
	{
		log.info("Get the data for team:[{}] for year:[{}]", teamName, year);
		try
		{
			Team team = validateAndGetTeam(teamName);
			if (isNotValidYear(year))
				throw new IllegalArgumentException("Invalid year");

			LocalDate startDateOfYear = LocalDate.of(year, 1, 1);
			LocalDate endDateOfYear = LocalDate.of(year, 12, 31);
			//@formatter:off
			List<MatchResponse> matches = matchService.getMatchesForTeamByYear(team.getTeamName(), startDateOfYear, endDateOfYear).stream()
				.filter(Objects::nonNull)
				.map(ResponseMapper::toMatchResponse)
				.toList();
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(new MatchesResponse(matches, new PageableResponse(matches.size(), 1, matches.size(), 0)));
			//@formatter:on
		}
		catch (DataNotFoundException e)
		{
			log.error("Given team:[{}] NOT found", teamName);
			return ResponseEntity.notFound().build();
		}
		catch (IllegalArgumentException e)
		{
			log.error("Given year:[{}] is NOT valid", year);
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).build();
		}
	}

	private Team validateAndGetTeam(String teamName) throws DataNotFoundException
	{
		return teamRepository.findByTeamName(teamName).orElseThrow(() -> new DataNotFoundException("Team not found"));
	}

	private boolean isNotValidYear(int year)
	{
		int fromYear = getYear(Sort.by("date").ascending());
		int toYear = getYear(Sort.by("date").descending());
		log.debug("Valid year range is from:[{}] to:[{}]", fromYear, toYear);
		return year < fromYear || year > toYear;
	}

	private int getYear(Sort sortBy)
	{
		//@formatter:off
		return matchService.getAllMatches(0, 1, sortBy).stream()
				.filter(Objects::nonNull)
				.findFirst()
				.map(Match::getDate)
				.orElseThrow(() -> new RuntimeException("Year data NOT found"))
				.getYear();
		//@formatter:on
	}
}
