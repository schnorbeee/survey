package com.dynata.surveyhw.repositories;

import com.dynata.surveyhw.entities.Participation;
import com.dynata.surveyhw.repositories.returns.SurveyStatisticAverage;
import com.dynata.surveyhw.repositories.returns.SurveyStatisticCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParticipationRepository extends CrudRepository<Participation, Long> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO participation (member_id, survey_id, status_id, length)
            VALUES (:#{#p.memberId}, :#{#p.surveyId}, :#{#p.statusId}, :#{#p.length})
            ON CONFLICT (member_id, survey_id, status_id)
            DO UPDATE SET length = EXCLUDED.length
            """, nativeQuery = true)
    void upsertParticipation(Participation p);

    @Query("SELECT p.surveyId AS survey_id, COUNT(p.memberId) AS member_count FROM Participation p "
            + "WHERE p.statusId = :statusId GROUP BY p.surveyId")
    List<SurveyStatisticCount> findStatisticCountsByStatus(@Param("statusId") Long statusId);

    @Query("SELECT p.surveyId AS survey_id, AVG(p.length) AS completed_average FROM Participation p "
            + "WHERE p.statusId = 4 GROUP BY p.surveyId")
    List<SurveyStatisticAverage> findStatisticLengthByStatus();
}
