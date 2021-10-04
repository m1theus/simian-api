package io.github.m1theus.simianapi.entrypoint.rest;

import io.github.m1theus.simianapi.core.domain.entity.DnaStats;
import io.github.m1theus.simianapi.core.usecase.DnaSimianUseCase;
import io.github.m1theus.simianapi.core.usecase.DnaStatsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Tag(name = "Simian API", description = "API that verifies dna sequences")
@RestController
@RequestMapping("/dna")
public class DnaController {
    private static final Logger logger = LoggerFactory.getLogger(DnaController.class);

    private final DnaSimianUseCase dnaSimianUseCase;
    private final DnaStatsUseCase dnaStatsUseCase;

    public DnaController(final DnaSimianUseCase dnaSimianUseCase, final DnaStatsUseCase dnaStatsUseCase) {
        this.dnaSimianUseCase = dnaSimianUseCase;
        this.dnaStatsUseCase = dnaStatsUseCase;
    }

    @Operation(summary = "Verify if dna sequence is human or simian")
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Return true for simian dna sequence",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(type = "object", implementation = DnaResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Return false for human dna sequence",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(type = "object", implementation = DnaResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    }
    )
    @PostMapping("/simian")
    public ResponseEntity<DnaResponse> simian(@Valid @RequestBody DnaRequest dnaRequest) {
        long start = System.currentTimeMillis();

        final boolean isSimian = dnaSimianUseCase.execute(dnaRequest);

        long measuredTime = System.currentTimeMillis() - start;

        logger.info("m=simian measured time for request={},ms={}ms,result={}", dnaRequest, measuredTime, isSimian);

        if (isSimian) {
            return ResponseEntity.ok(new DnaResponse(true));
        }

        return ResponseEntity.status(FORBIDDEN).body(new DnaResponse(false));
    }

    @Operation(summary = "Return statistics of stored dna sequences")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(type = "object", implementation = DnaResponse.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @GetMapping("/stats")
    public ResponseEntity<DnaStatsResponse> stats() {
        long start = System.currentTimeMillis();
        final DnaStats stats = dnaStatsUseCase.execute();
        long measuredTime = System.currentTimeMillis() - start;
        final DnaStatsResponse response = new DnaStatsResponse(stats);
        logger.info("m=stats measured time for response={},ms={}ms", response, measuredTime);
        return ResponseEntity.ok(response);
    }
}
