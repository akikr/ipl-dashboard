package com.akikr.ipldashboard.config;

import com.akikr.ipldashboard.repo.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
class JobCompletionListener extends JobExecutionListenerSupport
{
	private final EntityManager entityManager;

	public JobCompletionListener(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void afterJob(JobExecution jobExecution)
	{
		if (jobExecution.getStatus() == BatchStatus.COMPLETED)
		{
			//@formatter:off
			log.info("Batch JOB FINISHED!! Time to verify the results");

			//All the distinct Team-Data
			Map<String, Team> teamData = new HashMap<>();

			//Getting all the team names & number of times, those have played as Team-1
			entityManager.createQuery(
					"select  m.team1, count(*) from Match m group by m.team1", Object[].class)
					.getResultList().stream()
					.map(t -> new Team((String) t[0], (long) t[1]))
					.forEach(team -> teamData.put(team.getTeamName(), team));

			//Getting all the team names & number of times, those have played as Team-2
			entityManager.createQuery(
					"select  m.team2, count(*) from Match m group by m.team2", Object[].class)
					.getResultList()
					.forEach(t -> {
						Team team = teamData.get((String) t[0]);
						if (team != null)
							team.setTotalMatches(team.getTotalMatches() + (long) t[1]);
					});

			//Getting all the team names & number of times, those have won the matches
			entityManager.createQuery(
					"select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
					.getResultList()
					.forEach(t -> {
						Team team = teamData.get((String) t[0]);
						if (team != null)
							team.setTotalWins((long) t[1]);
					});

			//Persist all the Team-Data
			teamData.values().forEach(entityManager::persist);

			log.info("Total teams found:[{}]", teamData.size());
			//@formatter:on
		}
	}
}
