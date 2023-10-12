package com.springapp.ipldashboard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springapp.ipldashboard.model.Match;

public interface MatchRepository extends CrudRepository<Match,Long> {
    
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1,String team2,Pageable pageable);
    
    @Query("Select m from Match m where (m.team1=:teamName OR m.team2=:teamName) AND m.date BETWEEN :startDate AND :endDate order by date DESC")
    List<Match> getByTeamBetweenDates(@Param("teamName")String teamName,@Param("startDate")LocalDate startDate,@Param("endDate")LocalDate endDate);

    
    default List<Match> findLatestMatchesByTeam(String team,int count){
        return getByTeam1OrTeam2OrderByDateDesc(team,team,PageRequest.of(0,count));
    }
}
