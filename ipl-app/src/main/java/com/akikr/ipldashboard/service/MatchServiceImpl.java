package com.akikr.ipldashboard.service;

import com.akikr.ipldashboard.model.MatchResponse;
import com.akikr.ipldashboard.model.MatchesResponse;
import com.akikr.ipldashboard.model.PageableResponse;
import com.akikr.ipldashboard.repo.Match;
import com.akikr.ipldashboard.repo.MatchRepository;
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

import static com.akikr.ipldashboard.utils.ResponseMapper.mapToPageableResponse;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService
{
	private final MatchRepository matchRepository;

	public MatchServiceImpl(MatchRepository matchRepository)
	{
		this.matchRepository = matchRepository;
	}

	@Override
	public ResponseEntity<MatchesResponse> getAllMatches(int page, int size)
	{
		Sort sortByDateAsc = Sort.by("date").ascending();
		log.info("Get match data for page:[{}] and size:[{}] sortBy:[{}]", page, size, sortByDateAsc);
		Page<Match> matchPage = matchRepository.findAll(PageRequest.of(page, size, sortByDateAsc));
		PageableResponse pages = mapToPageableResponse(matchPage);
		//@formatter:off
		List<MatchResponse> matches = matchPage.get()
				.filter(Objects::nonNull)
				.map(ResponseMapper::toMatchResponse)
				.toList();

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(new MatchesResponse(matches, pages));
		//@formatter:on
	}

	@Override
	public List<Match> getAllMatches(int page, int size, Sort sortBy)
	{
		log.debug("Get all match data for page:[{}] and size:[{}] sortBy:[{}]", page, size, sortBy);
		return matchRepository.findAll(PageRequest.of(page, size, sortBy)).getContent();
	}

	@Override
	public List<Match> getLatestMatchesByTeam(String teamName, int limit)
	{
		log.debug("Get latest {} matches data for team:[{}]", limit, teamName);
		return matchRepository.findLatestMatchesByTeam(teamName, limit);
	}

	@Override
	public List<Match> getMatchesForTeamByYear(String teamName, LocalDate startDateOfYear, LocalDate endDateOfYear)
	{
		log.debug("Get matches data for team:[{}] between dates: {} and {}", teamName, startDateOfYear, endDateOfYear);
		return matchRepository.getMatchesByTeamBetweenDates(teamName, startDateOfYear, endDateOfYear);
	}
}
