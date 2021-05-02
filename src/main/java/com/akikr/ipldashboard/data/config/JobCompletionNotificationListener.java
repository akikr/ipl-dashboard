package com.akikr.ipldashboard.data.config;

import com.akikr.ipldashboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final EntityManager entityManager;

  @Autowired
  public JobCompletionNotificationListener(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      //All the distinct Team-Data
      Map<String, Team> teamData = new HashMap<>();

      //Getting all the team names & number of times, those have played as Team-1
      entityManager.createQuery("select  m.team1, count(*) from Match m group by m.team1", Object[].class)
              .getResultList()
              .stream()
              .map(t -> new Team((String) t[0], (long) t[1]))
              .forEach(team -> teamData.put(team.getTeamName(), team));

      //Getting all the team names & number of times, those have played as Team-2
      entityManager.createQuery("select  m.team2, count(*) from Match m group by m.team2", Object[].class)
              .getResultList()
              .stream()
              .forEach(t -> {
                Team team = teamData.get((String) t[0]);
                if (team !=null) team.setTotalMatches(team.getTotalMatches() + (long) t[1]);
              });

      //Getting all the team names & number of times, those have won the matches
      entityManager.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
              .getResultList()
              .stream()
              .forEach(t -> {
                Team team = teamData.get((String) t[0]);
                if (team !=null) team.setTotalWins((long) t[1]);
              });

      teamData.values().forEach(team -> entityManager.persist(team));

//      teamData.values().forEach(team -> System.out.println("TeamData: " + team));
//      jdbcTemplate.query("SELECT team1, team2, date FROM match",
//      (rs, row) -> "Team 1: " + rs.getString(1) + " Team 2: " + rs.getString(2) + " Date: " + rs.getString(3))
//      .forEach(match -> System.out.println(match));

    }
  }
}