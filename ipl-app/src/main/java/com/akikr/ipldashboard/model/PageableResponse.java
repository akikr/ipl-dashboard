package com.akikr.ipldashboard.model;

import java.io.Serializable;

public record PageableResponse(long totalCount, int totalPages, int pageSize, int pageNumber) implements Serializable
{

}
