package com.akikr.ipldashboard.data;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.akikr.ipldashboard.model.Match;

public class MatchDataProcesser implements ItemProcessor<MatchInput, Match> {

  private static final Logger LOG = LoggerFactory.getLogger(MatchDataProcesser.class);
  private static final String DECISION_TO_BAT = "bat";
  private static final String DECISION_TO_FIELD = "field";

  @Override
  public Match process(final MatchInput matchInput) throws Exception {
    LOG.info("Processing Match-Data from MatchInput");
    Match match = new Match();

    match.setId(Long.parseLong(matchInput.getId()));
    match.setCity(matchInput.getCity());
    match.setDate(LocalDate.parse(matchInput.getDate()));
    match.setPlayerOfMatch(matchInput.getPlayerOfmatch());
    match.setVenue(matchInput.getVenue());

    //Set Team 1 and Team 2 depending upon Toss-Decision
    match.setTeam1(getFirstInningsTeam(matchInput));
    match.setTeam2(getSecondInningsTeam(matchInput));

    match.setTossWinner(matchInput.getTossWinner());
    match.setTossDecision(matchInput.getTossDecision());
    match.setMatchWinner(matchInput.getWinner());
    match.setResult(matchInput.getResult());
    match.setResultMargin(match.getResultMargin());
    match.setUmpire1(matchInput.getUmpire1());
    match.setUmpire2(matchInput.getUmpire2());

    return match;
  }

  public String getFirstInningsTeam(MatchInput matchInput) {
    return (DECISION_TO_BAT.equalsIgnoreCase(matchInput.getTossDecision())) 
            ? matchInput.getTossWinner() 
            : matchInput.getTossWinner().equalsIgnoreCase(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
  }

  public String getSecondInningsTeam(MatchInput matchInput) {
    return (DECISION_TO_FIELD.equalsIgnoreCase(matchInput.getTossDecision()))
            ? matchInput.getTossWinner()
            : matchInput.getTossWinner().equalsIgnoreCase(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
  }
}
