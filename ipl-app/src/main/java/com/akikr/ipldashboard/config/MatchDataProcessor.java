package com.akikr.ipldashboard.config;

import com.akikr.ipldashboard.model.MatchInput;
import com.akikr.ipldashboard.repo.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

@Slf4j
public class MatchDataProcessor implements ItemProcessor<MatchInput, Match>
{
	private static final String DECISION_TO_BAT = "bat";
	private static final String DECISION_TO_FIELD = "field";

	@Override
	public Match process(final MatchInput matchInput) throws Exception
	{
		Match match = new Match();
		try
		{
			log.debug("Processing Match-Data from MatchInput id:[{}]", matchInput.getId());

			match.setId(Long.parseLong(matchInput.getId()));
			match.setCity(matchInput.getCity());
			match.setDate(LocalDate.parse(matchInput.getDate()));
			match.setPlayerOfMatch(matchInput.getPlayerOfMatch());
			match.setVenue(matchInput.getVenue());

			//Set Team 1 and Team 2 depending upon Toss-Decision
			match.setTeam1(getFirstInningsTeam(matchInput));
			match.setTeam2(getSecondInningsTeam(matchInput));

			match.setTossWinner(matchInput.getTossWinner());
			match.setTossDecision(matchInput.getTossDecision());
			match.setMatchWinner(matchInput.getWinner());
			match.setResult(matchInput.getResult());
			match.setResultMargin(matchInput.getResultMargin());
			match.setUmpire1(matchInput.getUmpire1());
			match.setUmpire2(matchInput.getUmpire2());

			log.debug("Match-Data processed from MatchInput as: {}", match);
		}
		catch (NumberFormatException e)
		{
			log.error("NumberFormatException occurred while parsing Match-Data from MatchInput, due to:[{}]", e.getMessage());
		}
		return match;
	}

	private static String getFirstInningsTeam(MatchInput matchInput)
	{
		return (DECISION_TO_BAT.equalsIgnoreCase(matchInput.getTossDecision())) ?
				matchInput.getTossWinner() :
				matchInput.getTossWinner().equalsIgnoreCase(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
	}

	private static String getSecondInningsTeam(MatchInput matchInput)
	{
		return (DECISION_TO_FIELD.equalsIgnoreCase(matchInput.getTossDecision())) ?
				matchInput.getTossWinner() :
				matchInput.getTossWinner().equalsIgnoreCase(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
	}
}
