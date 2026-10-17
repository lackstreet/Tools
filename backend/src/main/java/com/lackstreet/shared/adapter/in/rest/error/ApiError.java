package com.lackstreet.shared.adapter.in.rest.error;

public record ApiError (
        String code,
        String message,
        String requestId
) {
}
