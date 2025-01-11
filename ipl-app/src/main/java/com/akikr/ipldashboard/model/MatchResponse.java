package com.akikr.ipldashboard.model;

import java.io.Serializable;
import java.time.LocalDate;

public record MatchResponse(long id, String city, LocalDate date, String playerOfMatch, String venue, String team1, String team2,
							String tossWinner, String tossDecision, String matchWinner, String result, String resultMargin, String umpire1,
							String umpire2) implements Serializable
{

}
