package com.dynata.survayhw.handlers.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@Schema(description = "Error response")
public class ExceptionResponse {

    @Schema(description = "statusCode", example = "400")
    private HttpStatus statusCode;

    @Schema(description = "message")
    private String message;

    @Schema(description = "detail")
    private String detail;
}
