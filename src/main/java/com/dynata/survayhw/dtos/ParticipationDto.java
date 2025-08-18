package com.dynata.survayhw.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Participation record")
public class ParticipationDto {

    @Schema(description = "memberId", example = "4")
    @JsonProperty("Member Id")
    private Long memberId;

    @Schema(description = "surveyId", example = "4")
    @JsonProperty("Survey Id")
    private Long surveyId;

    @Schema(description = "statusId", example = "4")
    @JsonProperty("Status")
    private Long statusId;

    @Schema(description = "length", example = "14")
    @JsonProperty("Length")
    private Integer length;
}
