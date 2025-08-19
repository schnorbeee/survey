package com.dynata.surveyhw.controllers;

import com.dynata.surveyhw.dtos.SurveyDto;
import com.dynata.surveyhw.dtos.SurveyStatisticDto;
import com.dynata.surveyhw.handlers.responses.ExceptionResponse;
import com.dynata.surveyhw.services.CsvService;
import com.dynata.surveyhw.services.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final CsvService csvService;

    private final SurveyService surveyService;

    @Autowired(required = false)
    public SurveyController(CsvService csvService, SurveyService surveyService) {
        this.csvService = csvService;
        this.surveyService = surveyService;
    }

    @Operation(summary = "Save surveys from csv file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = SurveyDto.class)))),
            @ApiResponse(responseCode = "400", description = "Runtime error: HttpStatus.BAD_REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Fatal error: HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SurveyDto>> uploadSurveysCsv(@RequestParam("file") MultipartFile file) {
        List<SurveyDto> surveyDtoList = csvService.readFromCsv(file, SurveyDto.class);
        return ResponseEntity.ok(surveyService.saveSurveyDtos(surveyDtoList));
    }

    @Operation(summary = "Get survey list by memberId, and status is: Completed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = SurveyDto.class)))),
            @ApiResponse(responseCode = "400", description = "Runtime error: HttpStatus.BAD_REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Fatal error: HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping(path = "/by-member-id-and-completed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SurveyDto>> getByMemberIdAndIsCompleted(@RequestParam("memberId") Long memberId) {
        return ResponseEntity.ok(surveyService.getByMemberIdAndIsCompleted(memberId));
    }

    @Operation(summary = "Get one member completed surveys point by memberId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Map.class)))),
            @ApiResponse(responseCode = "400", description = "Runtime error: HttpStatus.BAD_REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Fatal error: HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping(path = "/by-member-id-completion-points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Integer>> getSurveyCompletionPointsByMemberId(
            @RequestParam("memberId") Long memberId) {
        return ResponseEntity.ok(surveyService.getSurveyCompletionPointsByMemberId(memberId));
    }

    @Operation(summary = "Get survey statistic list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = SurveyStatisticDto.class)))),
            @ApiResponse(responseCode = "400", description = "Runtime error: HttpStatus.BAD_REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Fatal error: HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping(path = "/all-statistic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SurveyStatisticDto>> getAllStatisticSurveys() {
        return ResponseEntity.ok(surveyService.getAllStatisticSurveys());
    }
}
