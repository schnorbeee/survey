package com.dynata.survayhw.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participation",
        uniqueConstraints = @UniqueConstraint(columnNames = {"memberId", "surveyId", "statusId"})
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long surveyId;

    private Long statusId;

    private Integer length;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyId", referencedColumnName = "survey_id", insertable = false, updatable = false)
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusId", referencedColumnName = "status_id", insertable = false, updatable = false)
    private Status status;
}
