package com.cohortteam6.controller;

import com.cohortteam6.dto.ReconRunRequest;
import com.cohortteam6.exception.TradeNotFoundException;
import com.cohortteam6.repository.ReconBreakRepository;
import com.cohortteam6.repository.entity.ReconBreak;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import com.cohortteam6.dto.ResolutionRequest;
import com.cohortteam6.dto.ReconResultResponse;
import com.cohortteam6.dto.PagedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/v1/recon")
@Tag(name = "recon", description = "Reconciliation operations")
@SecurityRequirement(name = "bearerAuth")
public class ReconController {

    private final ReconBreakRepository breaks;

    public ReconController(ReconBreakRepository breaks) { this.breaks = breaks; }

    @PostMapping("/run")
    @Operation(summary = "Trigger a reconciliation job (async)")
    public ResponseEntity<Map<String, String>> runRecon(@Valid @RequestBody ReconRunRequest req) {
        String jobId = UUID.randomUUID().toString();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Map.of("jobId", jobId, "status", "QUEUED"));
    }

    @GetMapping("/jobs/{jobId}/results")
    @Operation(summary = "Get results for a recon job")
    public PagedResponse<ReconResultResponse> results(@PathVariable String jobId, @PageableDefault(size = 50) Pageable pageable) {
        return PagedResponse.from(breaks.findAll(pageable), rb -> new ReconResultResponse(
                rb.getId(), rb.getTradeId(), rb.getDiscrepancyType(), rb.getStatus(), rb.getDetectedAt(), rb.getResolvedAt(), rb.getResolutionNote()
        ));
    }

    @PutMapping("/results/{id}/resolve")
    @Operation(summary = "Mark a recon break as RESOLVED with a note")
    public ResponseEntity<ReconResultResponse> resolve(@PathVariable Long id,
                                              @Valid @RequestBody ResolutionRequest body) {
        ReconBreak rb = breaks.findById(id)
                .orElseThrow(() -> new TradeNotFoundException(id.toString()));
        rb.resolve(body.note());
        rb = breaks.save(rb);
        return ResponseEntity.ok(new ReconResultResponse(
                rb.getId(), rb.getTradeId(), rb.getDiscrepancyType(), rb.getStatus(), rb.getDetectedAt(), rb.getResolvedAt(), rb.getResolutionNote()
        ));
    }
}
