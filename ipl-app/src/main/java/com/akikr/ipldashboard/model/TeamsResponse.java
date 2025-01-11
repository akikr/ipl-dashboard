package com.akikr.ipldashboard.model;

import java.io.Serializable;
import java.util.List;

public record TeamsResponse(List<TeamResponse> teams, PageableResponse pages) implements Serializable
{

}