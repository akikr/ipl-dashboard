package com.akikr.ipldashboard.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MatchInput
{
	private String id;
	private String city;
	private String date;
	private String playerOfMatch;
	private String venue;
	private String team1;
	private String team2;
	private String tossWinner;
	private String tossDecision;
	private String winner;
	private String result;
	private String resultMargin;
	private String method;
	private String umpire1;
	private String umpire2;
}
