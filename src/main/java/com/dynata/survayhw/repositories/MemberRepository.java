package com.dynata.survayhw.repositories;

import com.dynata.survayhw.entities.Member;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO member (member_id, full_name, email_address, is_active)
            VALUES (:#{#m.memberId}, :#{#m.fullName}, :#{#m.emailAddress}, :#{#m.isActive})
            ON CONFLICT (member_id)
            DO UPDATE SET full_name = EXCLUDED.full_name,
                          email_address = EXCLUDED.email_address,
                          is_active = EXCLUDED.is_active
            """,  nativeQuery = true)
    void upsertMember(Member m);

    @Query("SELECT m FROM Member m JOIN Participation p ON m.memberId = p.memberId "
            + " WHERE p.surveyId = :surveyId AND p.statusId = 4")
    List<Member> findBySurveyIdAndIsCompleted(@Param("surveyId") Long surveyId);

    @Query("SELECT m FROM Member m JOIN Participation p ON m.memberId = p.memberId "
            + "WHERE p.surveyId = :surveyId AND (p.statusId = 1 OR p.statusId = 2) "
            + "AND m.isActive = true")
    List<Member> findByNotParticipatedSurveyAndIsActive(@Param("surveyId") Long surveyId);
}
