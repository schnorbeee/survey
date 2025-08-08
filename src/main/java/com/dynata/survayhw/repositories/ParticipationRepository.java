package com.dynata.survayhw.repositories;

import com.dynata.survayhw.entities.Participation;
import com.dynata.survayhw.repositories.returns.SurveyStatisticAverage;
import com.dynata.survayhw.repositories.returns.SurveyStatisticCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipationRepository extends CrudRepository<Participation, Long> {

    @Query("SELECT p.surveyId AS surveyId, COUNT(p.memberId) AS count FROM Participation p "
            + "WHERE p.statusId = :statusId GROUP BY p.surveyId")
    List<SurveyStatisticCount> findStatisticCountsByStatus(@Param("statusId") Long statusId);

    @Query("SELECT p.surveyId AS surveyId, AVG(p.length) AS average FROM Participation p "
            + "WHERE p.statusId = 4 GROUP BY p.surveyId")
    List<SurveyStatisticAverage> findStatisticLengthByStatus();
}
