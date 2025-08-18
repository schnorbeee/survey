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
@Schema(description = "Status record")
public class StatusDto {

    @Schema(description = "statusId", example = "4")
    @JsonProperty("Status Id")
    private Integer statusId;

    @Schema(description = "name", example = "Completed")
    @JsonProperty("Name")
    private String name;
}
