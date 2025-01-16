package com.akikr.ipldashboard.config;

import com.akikr.ipldashboard.repo.Match;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.annotation.Transactional;

public class MatchItemWriter implements ItemWriter<Match>
{
	private final static String INSERT_QUERY = """
			INSERT INTO match (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2)
			VALUES (:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2)
		""";

	private final EntityManager entityManager;

	public MatchItemWriter(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void write(Chunk<? extends Match> items) throws Exception
	{
		items.forEach(match -> entityManager.createNativeQuery(INSERT_QUERY)
				.setParameter("id", match.getId())
				.setParameter("city", match.getCity())
				.setParameter("date", match.getDate())
				.setParameter("playerOfMatch", match.getPlayerOfMatch())
				.setParameter("venue", match.getVenue())
				.setParameter("team1", match.getTeam1())
				.setParameter("team2", match.getTeam2())
				.setParameter("tossWinner", match.getTossWinner())
				.setParameter("tossDecision", match.getTossDecision())
				.setParameter("matchWinner", match.getMatchWinner())
				.setParameter("result", match.getResult())
				.setParameter("resultMargin", match.getResultMargin())
				.setParameter("umpire1", match.getUmpire1())
				.setParameter("umpire2", match.getUmpire2())
				.executeUpdate());
	}
}
