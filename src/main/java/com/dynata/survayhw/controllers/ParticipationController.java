package com.dynata.survayhw.controllers;

import com.dynata.survayhw.dtos.ParticipationDto;
import com.dynata.survayhw.handlers.responses.ExceptionResponse;
import com.dynata.survayhw.services.CsvService;
import com.dynata.survayhw.services.ParticipationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    private final CsvService csvService;

    private final ParticipationService participationService;

    @Autowired
    public ParticipationController(CsvService csvService, ParticipationService participationService) {
        this.csvService = csvService;
        this.participationService = participationService;
    }

    @Operation(summary = "Save participations from csv file.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Runtime error: HttpStatus.BAD_REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Fatal error: HttpStatus.INTERNAL_SERVER_ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParticipationDto> uploadParticipationsCsv(@RequestParam("file") MultipartFile file) {
        List<ParticipationDto> participationDtos = csvService.readFromCsv(file, ParticipationDto.class);
        return participationService.saveParticipationDtos(participationDtos);
    }
}
