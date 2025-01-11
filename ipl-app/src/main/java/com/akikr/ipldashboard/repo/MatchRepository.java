package com.akikr.ipldashboard.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long>
{
	Page<Match> findAll(Pageable pageable);

	List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

	default List<Match> findLatestMatchesByTeam(String teamName, int limit)
	{
		return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, limit));
	}

	@Query("""
			SELECT m FROM Match m WHERE (m.team1 = :teamName OR m.team2 = :teamName) AND m.date BETWEEN :startDate AND :endDate
			ORDER BY m.date DESC""")
	List<Match> getMatchesByTeamBetweenDates(
			@Param("teamName") String teamName,
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);
}
