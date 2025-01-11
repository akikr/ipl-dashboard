package com.akikr.ipldashboard.model;

import java.io.Serializable;
import java.util.List;

public record MatchesResponse(List<MatchResponse> matches, PageableResponse pages) implements Serializable
{

}
