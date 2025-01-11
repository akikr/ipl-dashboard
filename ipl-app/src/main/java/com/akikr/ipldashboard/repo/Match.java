package com.akikr.ipldashboard.repo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Match
{
	@Id
	private long id;
	private String city;
	private LocalDate date;
	private String playerOfMatch;
	private String venue;
	private String team1;
	private String team2;
	private String tossWinner;
	private String tossDecision;
	private String matchWinner;
	private String result;
	private String resultMargin;
	private String umpire1;
	private String umpire2;

	@Override
	public String toString()
	{
		//@formatter:off
		return "Match {"
				+ "city=" + city
				+ ", date=" + date
				+ ", id=" + id
				+ ", matchWinner=" + matchWinner
				+ ", playerOfMatch=" + playerOfMatch
				+ ", result=" + result
				+ ", resultMargin=" + resultMargin
				+ ", team1=" + team1
				+ ", team2=" + team2
				+ ", tossDecision=" + tossDecision
				+ ", tossWinner=" + tossWinner
				+ ", umpire1=" + umpire1
				+ ", umpire2=" + umpire2
				+ ", venue=" + venue
			+ "}";
		//@formatter:on
	}
}
