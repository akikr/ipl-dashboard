package com.akikr.ipldashboard.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long>
{
	Page<Team> findAll(Pageable pageable);

	Optional<Team> findByTeamName(String teamName);
}
