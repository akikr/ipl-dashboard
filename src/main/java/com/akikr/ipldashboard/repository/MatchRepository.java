package com.akikr.ipldashboard.repository;

import com.akikr.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);
    
    default List<Match> findLatestMatchesByTeam(String teamName, int count) {
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
    
    @Query("SELECT m FROM Match m WHERE (m.team1 = :teamName OR m.team2 = :teamName) AND m.date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    List<Match> getMatchesByTeamBetweenDates(
        @Param("teamName") String teamName,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
        );
    //Not using this JPQL, instead using our own query in above method.
    // List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
    //     String teamName1, LocalDate startDate1, LocalDate endDate1,
    //     String teamName2, LocalDate startDate2, LocalDate endDate2
    // );
    // default List<Match> getMatchesByTeamBetweenDates(String teamName, LocalDate startDate, LocalDate endDate) {
    //     return getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
    //         teamName, startDate, endDate,
    //         teamName, startDate, endDate
    //         );
    // }
    
}
