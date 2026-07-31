package com.cohortteam6.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResolutionRequest(
        @NotBlank(message = "Note cannot be empty")
        @Size(max = 500, message = "Note is too long")
        String note
) {}
