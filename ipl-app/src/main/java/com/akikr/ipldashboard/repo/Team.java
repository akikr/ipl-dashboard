package com.akikr.ipldashboard.repo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Team
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String teamName;
	private long totalMatches;
	private long totalWins;

	@Transient
	private List<Match> matches;

	public Team(String teamName, long totalMatches)
	{
		this.teamName = teamName;
		this.totalMatches = totalMatches;
	}

	@Override
	public String toString()
	{
		//@formatter:off
		return "Team {"
                + "id=" + id
                + ", teamName='" + teamName + '\''
                + ", totalMatches=" + totalMatches
                + ", totalWins=" + totalWins
              + '}';
        //@formatter:on
	}
}
