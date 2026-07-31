package com.cohortteam6.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record StatusUpdate(
        @NotBlank
        @Pattern(regexp = "PENDING|MATCHED|UNMATCHED|DISPUTED|CANCELLED", message = "Invalid status")
        String status
) {}
