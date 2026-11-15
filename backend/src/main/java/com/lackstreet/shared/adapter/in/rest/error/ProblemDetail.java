package com.lackstreet.shared.adapter.in.rest.error;

import java.time.Instant;

public record ProblemDetail(
        String title,
        int status,
        String requestId,
        String detail,
        Instant timestamp
) {
    public static ProblemDetail badRequest(String requestId, String detail) {
        return new ProblemDetail("Invalid Request", 400, requestId, detail, Instant.now());
    }
}