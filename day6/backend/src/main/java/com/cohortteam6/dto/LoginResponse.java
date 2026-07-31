package com.cohortteam6.dto;

public record LoginResponse(String token, String tokenType, long expiresInSeconds, String role) {}
