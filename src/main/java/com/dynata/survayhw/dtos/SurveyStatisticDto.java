package com.dynata.survayhw.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SurveyStatisticDto {

    private Long surveyId;

    private String surveyName;

    private Long numberOfCompletes;

    private Long numberOfFilteredParticipants;

    private Long numberOfRejectedParticipants;

    private Double averageLengthOfTimeSpentOnSurvey;
}
