package com.dynata.survayhw.dtos;

import com.dynata.survayhw.utils.ZeroOneBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Member record")
public class MemberDto {

    @Schema(description = "memberId", example = "1")
    @JsonProperty("Member Id")
    private Long memberId;

    @Schema(description = "fullName", example = "Gipsz Jakab")
    @JsonProperty("Full name")
    private String fullName;

    @Schema(description = "emailAddress", example = "gipsz.jakab@mail.hu")
    @JsonProperty("E-mail address")
    private String emailAddress;

    @Schema(description = "isActive", example = "false")
    @JsonProperty("Is Active")
    @JsonDeserialize(using = ZeroOneBooleanDeserializer.class)
    private Boolean isActive;
}
