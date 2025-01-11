package com.akikr.ipldashboard.model;

import com.akikr.ipldashboard.repo.Match;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TeamResponse(long id, String teamName, long totalMatches, long totalWins, List<Match> matches) implements Serializable
{

}
